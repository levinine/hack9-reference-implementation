/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.rest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.levi9.hack9.reference.api.SwitchApiController;
import com.levi9.hack9.reference.api.model.Call;
import com.levi9.hack9.reference.api.model.CallCost;
import com.levi9.hack9.reference.api.model.Price;
import com.levi9.hack9.reference2019.service.CallService;
import com.levi9.hack9.reference2019.service.PriceService;

/**
 * @author n.milutinovic
 *
 */
@RestController
public class SwitchboardController extends SwitchApiController {
	private PriceService priceResolver;
	private CallService callService;
	
	public SwitchboardController(NativeWebRequest request, PriceService priceResolver, CallService callService) {
		super(request);
		this.priceResolver = priceResolver;
		this.callService = callService;
	}
	
	@Override
    public ResponseEntity<Price> getPrice(
    		@NotNull @Valid @RequestParam(value = "number", required = true) String number,
    		@Valid @RequestParam(value = "time", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime time) {
		final Instant callTime = (time == null) ? Instant.now() : time.toInstant();
		return ResponseEntity.of(
				priceResolver.resolve(number, callTime)
				.map(price -> {
					final Price response = new Price();
					response.setFrom(price.start.atOffset(ZoneOffset.ofHours(0)));
					response.setPrefix(price.prefix);
					response.setPrice(price.price);
					response.setInitial(BigDecimal.valueOf(price.initial));
					response.setIncrement(BigDecimal.valueOf(price.increment));
					return response;
				}));
    }
	
	@Override
	public ResponseEntity<CallCost> registerCall(@Valid @RequestBody Call call) {
		return ResponseEntity.of(callService.registerCall(call));
	}
}
