/**
 * 
 */
package com.levi9.hack9.reference2019.service.impl;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.levi9.hack9.reference.api.model.Invoice;
import com.levi9.hack9.reference.api.model.InvoiceRequest;
import com.levi9.hack9.reference.api.model.Invoices;
import com.levi9.hack9.reference.api.model.Report;
import com.levi9.hack9.reference.api.model.ReportInvoiceItem;
import com.levi9.hack9.reference2019.service.FinancialService;

/**
 * @author n.milutinovic
 *
 */
@Service
@Transactional
public class FinancialServiceDbImpl implements FinancialService {
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	@Autowired
	RestTemplate rest;

	@Override
	@Transactional(readOnly = false)
	public void createInvoices(InvoiceRequest request) {
		Long masterId = getInvoiceMasterId();
		final Instant start = request.getStart().toInstant();
		final Instant end = request.getEnd().toInstant();
		markInvoices(masterId, start, end);
		final List<Invoice> invoices = sumUpInvoices(masterId, start, end);
		insertInvoices(invoices, masterId);
		callback(request, masterId.toString(), invoices);
	}
	
	private Long getInvoiceMasterId() {
		final String GET_GLOBAL_ID = "SELECT invoice_id.nextval AS master_id";
		return jdbc.query(GET_GLOBAL_ID, rs -> {
			rs.next();
			return rs.getLong("master_id");
		});
	}
	
	private void markInvoices(Long masterId, Instant start, Instant end) {
		final String MARK_INVOICE = "UPDATE Calls SET invoice_id = CONCAT(:id, CONCAT('-', caller)), master_invoice_id=:master_id "
				+ "WHERE started >= :start AND started <= :end AND invoice_id IS NULL";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", masterId.toString());
		parameters.addValue("master_id", masterId);
		parameters.addValue("start", start);
		parameters.addValue("end",  end);
		
		jdbc.update(MARK_INVOICE, parameters);
	}

	private List<Invoice> sumUpInvoices(Long masterId, Instant start, Instant end) {
		final String SUM_UP = "SELECT caller, invoice_id, SUM(cost) AS sum, COUNT(*) AS count FROM Calls "
				+ "WHERE master_invoice_id=:master_id "
				+ "GROUP BY invoice_id, caller";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource("master_id", masterId);
		return jdbc.query(SUM_UP, parameters, (rs, rowNum) -> {
			return new Invoice()
					.id(rs.getString("invoice_id"))
					.calling(rs.getString("caller"))
					.count(rs.getInt("count"))
					.end(end.atOffset(ZoneOffset.ofHours(0)))
					.start(start.atOffset(ZoneOffset.ofHours(0)))
					.sum(rs.getFloat("sum"));
		});
	}
	
	private void insertInvoices(List<Invoice> invoices, Long masterId) {
		final String CREATE_INVOICES = "INSERT INTO Invoices (id, master_id, calling, start, end, sum, count) "
				+ "VALUES (:id, :master_id, :calling, :start, :end, :sum, :count)";
		
		SqlParameterSource[] parametersBatch = invoices.stream().map(invoice -> {
			final MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("id", invoice.getId());
			parameters.addValue("master_id", masterId);
			parameters.addValue("calling", invoice.getCalling());
			parameters.addValue("start", invoice.getStart());
			parameters.addValue("end", invoice.getEnd());
			parameters.addValue("sum", invoice.getSum());
			parameters.addValue("count", invoice.getCount());
			return parameters;
		}).toArray(SqlParameterSource[]::new);
		jdbc.batchUpdate(CREATE_INVOICES, parametersBatch);
	}
	
	private void callback(InvoiceRequest request, String masterId, List<Invoice> invoices) {
		final Invoices report = new Invoices().masterId(masterId).invoices(invoices);
		rest.postForEntity(request.getCallback(), report, Void.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Invoice> getInvoice(String id) {
		final String sql = "SELECT * FROM Invoices WHERE id = :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource("id", id);
		return jdbc.query(sql, parameters, rs -> {
			return rs.next() ?
					Optional.of(new Invoice()
							.calling(rs.getString("calling"))
							.count(rs.getInt("count"))
							.end(TimeUtil.convert(rs.getTimestamp("end")))
							.id(rs.getString("id"))
							.start(TimeUtil.convert(rs.getTimestamp("start")))
							.sum(rs.getFloat("sum")))
					: Optional.empty();
		});
	}
	
	@Override
	@Transactional(readOnly = true)
	public Report getReport(String caller) {
		final String invoicesSql = "SELECT * FROM Invoices WHERE calling = :caller";
		final String unInvoicedSql = "SELECT SUM(cost) as uninvoiced, COUNT(*) FROM Calls WHERE invoice_id IS NULL and caller = :caller";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource("caller", caller);
		final List<ReportInvoiceItem> invoices = jdbc.query(invoicesSql, parameter, (rs, rowNum) -> {
			return new ReportInvoiceItem()
					.id(rs.getString("id"))
					.sum(rs.getFloat("sum"));
		});
		return jdbc.query(unInvoicedSql, parameter, rs -> {
			return rs.next() ?
					new Report()
						.calling(caller)
						.invoices(invoices)
						.remaining(rs.getFloat("uninvoiced"))
					: new Report().calling(caller);
		});
	}
}
