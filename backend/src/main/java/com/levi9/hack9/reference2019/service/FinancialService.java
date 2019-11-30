/**
 * 
 */
package com.levi9.hack9.reference2019.service;

import com.levi9.hack9.reference.api.model.InvoiceRequest;

/**
 * @author n.milutinovic
 *
 */
public interface FinancialService {
	/**
	 * Batch create invoices for all numbers in the given period.
	 * 
	 * @param request parameters for creating invoices
	 */
	void createInvoices(InvoiceRequest request);
}
