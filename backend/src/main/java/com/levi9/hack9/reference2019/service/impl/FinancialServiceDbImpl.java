/**
 * 
 */
package com.levi9.hack9.reference2019.service.impl;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.levi9.hack9.reference.api.model.Invoice;
import com.levi9.hack9.reference.api.model.InvoiceRequest;
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

	@Override
	@Transactional(readOnly = false)
	public void createInvoices(InvoiceRequest request) {
		Long masterId = getInvoiceMasterId();
		final Instant start = request.getStart().toInstant();
		final Instant end = request.getEnd().toInstant();
		markInvoices(masterId, start, end);
		insertInvoices(sumUpInvoices(masterId, start, end), masterId);
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
}
