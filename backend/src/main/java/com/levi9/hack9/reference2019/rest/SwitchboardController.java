/**
 * 
 */
package com.levi9.hack9.reference2019.rest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.levi9.hack9.reference.api.ApiUtil;
import com.levi9.hack9.reference.api.SwitchboardApiController;
import com.levi9.hack9.reference.api.model.Call;
import com.levi9.hack9.reference.api.model.CallCost;
import com.levi9.hack9.reference.api.model.Error;
import com.levi9.hack9.reference.api.model.Price;
import com.levi9.hack9.reference2019.service.PriceResolver;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	
	@ApiOperation(value = "Get call price", nickname = "getPrice", notes = "Get (potential) call price for the given called number, per minute. This will not initiate a call.", response = Price.class, tags={ "call", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Price of a call, per second", response = Price.class),
        @ApiResponse(code = 404, message = "Price for the number cannot be calculated.", response = Error.class),
        @ApiResponse(code = 400, message = "Call number is invalid format", response = Error.class) })
    @RequestMapping(value = "/switchboard/price",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    public ResponseEntity<Price> getPrice(@NotNull @ApiParam(value = "Telephone number to call, for which the call price should be calculated.", required = true) @Valid @RequestParam(value = "number", required = true) String number,@NotNull @ApiParam(value = "time of the call.", required = true) @Valid @RequestParam(value = "time", required = true) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) LocalDateTime time) {
		final Instant callTime = time.toInstant(ZoneOffset.ofHours(0));
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
