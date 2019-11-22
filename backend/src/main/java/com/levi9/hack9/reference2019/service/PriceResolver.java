/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.service;

import java.time.Instant;
import java.util.Optional;

/**
 * Price resolver service.
 * 
 * @author n.milutinovic
 */
public interface PriceResolver {
	/**
	 * Given telephone number, determine the price, if possible.
	 * 
	 * @param telephone telephone number, optional spaces removed.
	 * @return Price for the call to the given number, if it is listed, {@code None} otherwise.
	 */
	Optional<Float> resolve(String telephone, Instant time);
}
