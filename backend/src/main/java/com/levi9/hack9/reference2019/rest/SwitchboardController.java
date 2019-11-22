/**
 * 
 */
package com.levi9.hack9.reference2019.rest;

import java.time.Instant;
import java.time.OffsetDateTime;

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
		final Float price = priceResolver.resolve(number, callTime).get();
		final Price response = new Price();
		response.setFrom(time); // TODO Fix this in lookup.
		response.setTo(time);
		response.setPrefix(number);
		response.setPrice(price);
		return ResponseEntity.ok(response);

    }
	
	@Override
	public ResponseEntity<CallCost> registerCall(@Valid Call call) {
		// TODO Auto-generated method stub
		return super.registerCall(call);
	}
}
