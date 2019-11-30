/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.service;

import java.util.Optional;

import com.levi9.hack9.reference.api.model.Invoice;
import com.levi9.hack9.reference.api.model.InvoiceRequest;
import com.levi9.hack9.reference.api.model.Report;

/**
 * Financial services.
 * 
 * @author n.milutinovic
 */
public interface FinancialService {
	/**
	 * Batch create invoices for all numbers in the given period.
	 * 
	 * @param request parameters for creating invoices
	 */
	void createInvoices(InvoiceRequest request);
	/**
	 * Retrieve one invoice, by ID.
	 * 
	 * @param id invoice id, in the form {@literal $master_id-$caller}
	 * @return
	 */
	Optional<Invoice> getInvoice(String id);
	/**
	 * Get the financial report on the caller. The report will list
	 * all invoices and the un-invoiced amount.
	 * 
	 * @param caller telephone number for which to get the report
	 * @return Report on the calling numbers financial status.
	 */
	Report getReport(String caller);
}
