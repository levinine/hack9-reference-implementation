/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.levi9.hack9.reference2019.service.InvoiceIdService;

/**
 * H2 DB implementation of invoice master ID, based on a sequence.
 * 
 * @author n.milutinovic
 */
@Service("h2-invoice-id")
@Profile("db-h2")
public class H2InvoiceIdService implements InvoiceIdService {
	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	@Override
	public Long getNextMasterId() {
		final String GET_GLOBAL_ID = "SELECT invoice_id.nextval AS master_id";
		return jdbc.query(GET_GLOBAL_ID, rs -> {
			rs.next();
			return rs.getLong("master_id");
		});
	}
}
