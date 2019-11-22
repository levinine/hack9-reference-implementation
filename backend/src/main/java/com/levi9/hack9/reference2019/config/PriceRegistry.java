/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.config;

import java.time.Instant;
import java.util.Optional;

/**
 * @author n.milutinovic
 *
 */
public interface PriceRegistry {
	/**
	 * Lookup call price, per-minute, for the given phone number.
	 * 
	 * @param telephone telephone number to lookup the price.
	 * @return price, if one is found.
	 */
	Optional<Float> lookup(String telephone, Instant timeOfCall);
}
