/**
 * 
 */
package com.levi9.hack9.reference2019.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.NativeWebRequest;

import com.levi9.hack9.reference.api.FinancialApiController;
import com.levi9.hack9.reference.api.model.InvoiceRequest;
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
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResponseEntity<Void> createInvoice(@Valid @RequestBody InvoiceRequest invoiceRequest) {
		service.createInvoices(invoiceRequest);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
