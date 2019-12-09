/**
 * 
 */
package com.levi9.hack9.reference2019.service.impl;

import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.levi9.hack9.reference2019.service.InvoiceIdService;

/**
 * @author n.milutinovic
 *
 */
@Service("mysql-invoice-id")
@Profile("db-mysql")
public class MySqlInvoiceIdService implements InvoiceIdService {
	@Autowired
	private JdbcTemplate jdbc;

	@Override
	@Transactional
	public Long getNextMasterId() {
		final String SQL = "INSERT INTO `MasterInvoiceId` VALUES ()";
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(connection -> connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS), keyHolder);
		return keyHolder.getKey().longValue();
	}
}
