/*
 * Copyright Levi Nine, 2019.
 */
package com.levi9.hack9.reference2019.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.levi9.hack9.reference.api.ResetApiController;
import com.levi9.hack9.reference2019.service.ResetService;

/**
 * Controller to reset application state.
 * 
 * @author n.milutinovic
 */
@RestController
public class ResetController extends ResetApiController {
	@Autowired
	private ResetService reset;
	
	public ResetController(NativeWebRequest request) {
		super(request);
	}

	@Override
	public ResponseEntity<Void> reset() {
		reset.reset();
		return ResponseEntity.created(URI.create("/reference/reset")).build();
	}
}
