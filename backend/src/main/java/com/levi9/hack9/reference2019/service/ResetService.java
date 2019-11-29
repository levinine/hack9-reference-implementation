/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.service;

/**
 * Reset system calls.
 * 
 * @author n.milutinovic
 */
public interface ResetService {
	/**
	 * Reset system, deleting all call and financial records.
	 */
	void reset();
	/**
	 * Reset system and reimport prices.
	 */
	void initialize();
}
