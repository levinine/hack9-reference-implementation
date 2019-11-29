/**
 * 
 */
package com.levi9.hack9.reference2019.rest;

import java.time.OffsetDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;

import com.levi9.hack9.reference.api.ListingApiController;
import com.levi9.hack9.reference.api.model.Listing;
import com.levi9.hack9.reference2019.service.CallService;

/**
 * @author n.milutinovic
 *
 */
@Controller
public class ListingController extends ListingApiController {
	@Autowired
	private CallService service;
	/**
	 * @param request
	 */
	public ListingController(NativeWebRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResponseEntity<Listing> listing(
			@PathVariable("calling") String calling,
			@NotNull @Valid @RequestParam(value = "from", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
			@NotNull @Valid @RequestParam(value = "to", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to) {
		return ResponseEntity.ok(service.getListing(calling, from.toInstant(), to.toInstant()));
	}
}
