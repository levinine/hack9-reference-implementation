/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.NativeWebRequest;

import com.levi9.hack9.reference.api.FinancialApiController;
import com.levi9.hack9.reference.api.model.Invoice;
import com.levi9.hack9.reference.api.model.InvoiceRequest;
import com.levi9.hack9.reference.api.model.Report;
import com.levi9.hack9.reference2019.service.FinancialService;

/**
 * @author n.milutinovic
 *
 */
@Controller
public class FinancialController extends FinancialApiController {
	@Autowired
	private FinancialService service;
	
	/**
	 * @param request
	 */
	public FinancialController(NativeWebRequest request) {
		super(request);
	}

	@Override
	public ResponseEntity<Void> createInvoice(@Valid @RequestBody InvoiceRequest invoiceRequest) {
		service.createInvoices(invoiceRequest);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	
	@Override
	public ResponseEntity<Invoice> getInvoice(@PathVariable("id") String id) {
		return ResponseEntity.of(service.getInvoice(id));
	}
	
	@Override
	public ResponseEntity<Report> getReport(@PathVariable("calling") String calling) {
		return ResponseEntity.ok(service.getReport(calling));
	}
}
