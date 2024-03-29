/**
 * 
 */
package com.levi9.hack9.reference2019.service.impl;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.levi9.hack9.reference2019.config.PriceInterval;
import com.levi9.hack9.reference2019.config.PriceRegistry;
import com.levi9.hack9.reference2019.service.PriceService;

/**
 * @author n.milutinovic
 *
 */
@Service
public class InMemoryPriceResolver implements PriceService {
	private final PriceRegistry registry;
	
	public InMemoryPriceResolver(PriceRegistry registry) {
		this.registry = registry;
	}
	
	@Override
	public Optional<PriceInterval> resolve(String telephone, Instant time) {
		return registry.lookup(telephone, time);
	}

}
