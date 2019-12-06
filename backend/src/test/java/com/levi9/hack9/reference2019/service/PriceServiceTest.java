/**
 * 
 */
package com.levi9.hack9.reference2019.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.levi9.hack9.reference2019.config.PriceConfig;
import com.levi9.hack9.reference2019.config.PriceInterval;
import com.levi9.hack9.reference2019.config.PriceRegistryImpl;
import com.levi9.hack9.reference2019.service.impl.InMemoryPriceResolver;

/**
 * @author n.milutinovic
 *
 */
public class PriceServiceTest {
	private PriceService resolver;
	private final Resource csv = new ClassPathResource("prices.csv");
	
	@BeforeEach
	public void setUp() throws IOException {
		resolver = new InMemoryPriceResolver(
				new PriceRegistryImpl(
						new PriceConfig(csv)));
	}
	
	@Test
	public void test_novi_sad() {
		final PriceInterval price = resolver.resolve("+381 21 123456", Instant.parse("2019-02-01T00:00:00.00Z")).get();
		
		assertEquals(2.34F, price.price, "Price = 2.34");
		assertEquals(5, price.initial, "Initial = 5");
		assertEquals(20, price.increment, "Increment = 20");
	}
	
	@Test
	public void test_novi_sad_second_half() {
		final PriceInterval price = resolver.resolve("+381 21 123456", Instant.parse("2019-08-01T00:00:00.00Z")).get();
		
		assertEquals(3.24F, price.price, "Price = 3.24");
		assertEquals(5, price.initial, "Initial = 5");
		assertEquals(20, price.increment, "Increment = 20");
	}
	
	@Test
	public void test_wrong_number() {
		final Optional<PriceInterval> price = resolver.resolve("+381 11 123456", Instant.parse("2019-02-01T00:00:00.00Z"));
		
		assertFalse(price.isPresent(), "No price for Belgrade");
	}
	
	@Test
	public void test_wrong_year() {
		final Optional<PriceInterval> price = resolver.resolve("+381 21 123456", Instant.parse("2018-02-01T00:00:00.00Z"));
		
		assertFalse(price.isPresent(), "No price for year 2018");
	}
	
	@Test
	public void test_mts_deep_prefix() {
		final PriceInterval price = resolver.resolve("+381 64 123456", Instant.parse("2019-02-01T00:00:00.00Z")).get();
		
		assertEquals(6.0F, price.price, "Price = 6.0");
		assertEquals(20, price.initial, "Initial = 5");
		assertEquals(10, price.increment, "Increment = 20");
	}
}
