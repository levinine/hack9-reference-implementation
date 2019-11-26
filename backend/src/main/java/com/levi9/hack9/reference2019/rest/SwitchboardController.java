/**
 * 
 */
package com.levi9.hack9.reference2019.rest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.levi9.hack9.reference.api.SwitchboardApiController;
import com.levi9.hack9.reference.api.model.Call;
import com.levi9.hack9.reference.api.model.CallCost;
import com.levi9.hack9.reference.api.model.Price;
import com.levi9.hack9.reference2019.config.PriceInterval;
import com.levi9.hack9.reference2019.service.PriceResolver;

/**
 * @author n.milutinovic
 *
 */
@RestController
public class SwitchboardController extends SwitchboardApiController {
	private PriceResolver priceResolver;
	
	public SwitchboardController(NativeWebRequest request, PriceResolver priceResolver) {
		super(request);
		this.priceResolver = priceResolver;
	}
	
	@Override
    public ResponseEntity<Price> getPrice(
    		@NotNull @Valid @RequestParam(value = "number", required = true) String number,
    		@NotNull @Valid @RequestParam(value = "time", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime time) {
		final Instant callTime = time.toInstant();
		final PriceInterval price = priceResolver.resolve(number, callTime).get();
		final Price response = new Price();
		response.setFrom(price.start.atOffset(ZoneOffset.ofHours(0)));
		response.setPrefix(price.prefix);
		response.setPrice(price.price);
		response.setInitial(BigDecimal.valueOf(price.initial));
		response.setIncrement(BigDecimal.valueOf(price.increment));
		return ResponseEntity.ok(response);

    }
	
	@Override
	public ResponseEntity<CallCost> registerCall(@Valid Call call) {
		// TODO Auto-generated method stub
		return super.registerCall(call);
	}
}
