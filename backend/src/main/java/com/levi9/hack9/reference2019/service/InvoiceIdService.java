/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.service;

/**
 * Get invoice master ID.
 * 
 * It seems every RDBMS has its own way of dealing with sequences.
 * Implementations of this interface will cater for those differences.
 * 
 * @author n.milutinovic
 */
public interface InvoiceIdService {
	/**
	 * Generate next Master Invoice ID.
	 * 
	 * @return invoice master ID.
	 */
	Long getNextMasterId();
}
