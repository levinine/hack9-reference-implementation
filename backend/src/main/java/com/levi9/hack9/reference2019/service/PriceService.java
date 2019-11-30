/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.service;

import java.time.Instant;
import java.util.Optional;

import com.levi9.hack9.reference2019.config.PriceInterval;

/**
 * Price resolver service.
 * 
 * @author n.milutinovic
 */
public interface PriceService {
	/**
	 * Given telephone number, determine the price, if possible.
	 * 
	 * @param telephone telephone number, optional spaces removed.
	 * @return Price for the call to the given number, if it is listed, {@code None} otherwise.
	 */
	Optional<PriceInterval> resolve(String telephone, Instant time);
}
