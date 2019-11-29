/**
 * 
 */
package com.levi9.hack9.reference2019.service.impl;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.levi9.hack9.reference2019.config.PriceConfig;
import com.levi9.hack9.reference2019.service.ResetService;

/**
 * @author n.milutinovic
 *
 */
@Service
public class ResetServiceDbImpl implements ResetService {
	@Autowired
	private JdbcTemplate jdbc;
	private NamedParameterJdbcTemplate template;
	
	@Autowired
	private PriceConfig prices;
	
	@Override
	public void reset() {
		jdbc.execute("DELETE FROM Calls");
		jdbc.execute("DELETE FROM Invoices");

	}

	@Override
	public void initialize() {
		final String sqlInsert = "INSERT INTO Prices (id, country, city, prefix, valid_from, price, initial, increment)"
				+ " VALUES (:id, :country, :city, :prefix, :start, :price, :initial, :increment)";
		
		jdbc.execute("DELETE FROM Prices");
		try {
			SqlParameterSource[] parameters = prices.getPrices().stream()
				.map(price -> {
					MapSqlParameterSource source = new MapSqlParameterSource();
					source.addValue("id", price.prefix + "-" + price.start);
					source.addValue("city", price.city);
					source.addValue("country", price.country);
					source.addValue("increment", price.increment);
					source.addValue("initial", price.initial);
					source.addValue("prefix", price.prefix);
					source.addValue("price", price.price);
					source.addValue("start", price.start);
					return source;
				})
				.collect(Collectors.toList()).toArray(new SqlParameterSource[0]);
			template.batchUpdate(sqlInsert, parameters);
		} catch (IOException e) {
			
		}
		
	}

}
