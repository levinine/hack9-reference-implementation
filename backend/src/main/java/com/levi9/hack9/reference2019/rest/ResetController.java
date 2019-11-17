/*
 * Copyright Levi Nine, 2019.
 */
package com.levi9.hack9.reference2019.rest;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.levi9.hack9.reference.api.ResetApiController;

/**
 * Controller to reset application state.
 * 
 * TODO Work in progress.
 * 
 * @author n.milutinovic
 */
@RestController
public class ResetController extends ResetApiController {

	public ResetController(NativeWebRequest request) {
		super(request);
	}

	@Override
	public ResponseEntity<Void> reset() {
		// TODO return correct URL from the context path
		return ResponseEntity.created(URI.create("/reference/reset")).build();
	}
}
