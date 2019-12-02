/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.judge.rest;

import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.NativeWebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.levi9.hack9.reference.control.api.CallbackApiController;
import com.levi9.hack9.reference.control.api.model.Invoices;

/**
 * Callback controller, accepting callbackl submissions.
 * 
 * @author n.milutinovic
 */
@Controller
public class CallbackController extends CallbackApiController {
	private static final Logger LOG = LoggerFactory.getLogger(CallbackController.class);
	
	/**
	 * @param request store the native request.
	 */
	public CallbackController(NativeWebRequest request) {
		super(request);
	}

	/**
	 * Accept invoice callback submission and log it on console.
	 * 
	 * @param token invoice request token.
	 * @param invoices the invoices submission.
	 * @return return 204
	 */
	@Override
	public ResponseEntity<Void> callbackInvoices(@PathVariable("token") UUID token, @Valid @RequestBody Invoices invoices) {
		LOG.info("Received token: " + token);
		ObjectMapper mapper = new ObjectMapper();
		try {
			LOG.info("Received Invoices: " + mapper.writer().writeValueAsString(invoices));
			return ResponseEntity.noContent().build();
		} catch (JsonProcessingException e) {
			LOG.error("Error serializing invoice", e);
			return ResponseEntity.unprocessableEntity().build();
		}
	}
}
