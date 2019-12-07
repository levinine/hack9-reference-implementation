/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.service.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.levi9.hack9.reference.api.model.Call;
import com.levi9.hack9.reference.api.model.CallCost;
import com.levi9.hack9.reference.api.model.Listing;
import com.levi9.hack9.reference.api.model.Price;
import com.levi9.hack9.reference2019.config.PriceInterval;
import com.levi9.hack9.reference2019.service.CallService;
import com.levi9.hack9.reference2019.service.PriceService;

/**
 * Implementation of call service based on Spring JDBC.
 * 
 * @author n.milutinovic
 */
@Service
public class CallServiceDbImpl implements CallService {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private PriceService priceResolver;
	
	@Override
	public Optional<Price> getPrice(String called, Instant time) {
		return priceResolver.resolve(called, time)
				.map(CallServiceDbImpl::convert);
	}

	private static Price convert(PriceInterval interval) {
		final Price price = new Price();
		price.setFrom(interval.start.atOffset(ZoneOffset.ofHours(0)));
		price.setPrefix(interval.prefix);
		price.setPrice(interval.price);
		price.setInitial(BigDecimal.valueOf(interval.initial));
		price.setIncrement(BigDecimal.valueOf(interval.increment));
		return price;
	}

	@Override
	public Optional<CallCost> registerCall(final Call call) {
		return getPrice(call.getCalled(), call.getStart().toInstant())
				.map(price -> {
					storeCall(price, call);
					return getCallCost(price, call);
				});
	}
	
	private static CallCost getCallCost(Price price, Call call) {
		return new CallCost()
				.called(call.getCalled())
				.calling(call.getCalling())
				.cost(calculateCost(price, call.getDuration()))
				.duration(call.getDuration())
				.price(price.getPrice())
				.rounded(price.getIncrement().intValue())
				.start(call.getStart());
		// TODO missing increment
	}
	
	private void storeCall(Price price, Call call) {
		final String REGISTER_CALL = "INSERT INTO Calls (caller, called, started, duration, cost, price_id, invoice_id)"
				+ "VALUES (:caller, :called, :started, :duration, :cost, :price_id, null)";
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("caller", call.getCalling());
		parameters.addValue("called", call.getCalled());
		parameters.addValue("started", call.getStart());
		parameters.addValue("duration", call.getDuration());
		parameters.addValue("cost", calculateCost(price, call.getDuration()));
		parameters.addValue("price_id", getPriceId(price));
		jdbcTemplate.update(REGISTER_CALL, parameters);
		
		
	}
	
	private static String getPriceId(Price price) {
		return price.getPrefix() + "-" + price.getFrom().format(DateTimeFormatter.ISO_INSTANT);
	}
	
	private static float calculateCost(Price price, int duration) {
		final int effective = duration + price.getInitial().intValue();
		final int increment = price.getIncrement().intValue();
		final int remainder = effective % increment;
		final int actual =  (remainder == 0) ? effective : (effective - remainder + increment);
		return price.getPrice() * actual / 60;
	}

	@Override
	public Listing getListing(String caller, Instant from, Instant to) {
		final String GET_LISTING = "SELECT "
				+ "c.called AS called, c.started AS started, c.duration AS duration, c.cost AS cost, "
				+ "p.price AS price, p.initial AS initial, p.increment AS increment "
				+ "FROM Calls c "
				+ "JOIN Prices p ON p.id = c.price_id "
				+ "WHERE c.caller = :caller AND started >= :from AND started <= :to";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("caller", caller);
		parameters.addValue("from", from.atOffset(ZoneOffset.ofHours(0)));
		parameters.addValue("to", to.atOffset(ZoneOffset.ofHours(0)));
		final List<CallCost> calls = jdbcTemplate.query(GET_LISTING, parameters, (ResultSet rs, int rowNum) -> {
			final CallCost callCost = new CallCost()
					.calling(caller)
					.called(rs.getString("called"))
					.start(TimeUtil.convert(rs.getTimestamp("started")))
					.duration(rs.getInt("duration"))
					.cost(rs.getFloat("cost"))
					.price(rs.getFloat("price"));
			return callCost;
		});
		return new Listing().calling(caller).calls(calls);
	}
	
	/* private static float calcCost(Price price, int duration) {
		final double increment = price.getIncrement().doubleValue();
		final double effective = Math.ceil((duration + price.getInitial().doubleValue()) / increment) * increment;
		return (float) (price.getPrice() * effective / 60);
	} */
}
