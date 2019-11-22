/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test price registry.
 * 
 * @author n.milutinovic
 */
public class PriceRegistryTest {
	private static final Instant FIRTS_OF_MARCH = Instant.parse("2019-03-01T00:00:00.00Z");
	private static final Instant FIRST_OF_SEP = Instant.parse("2019-09-01T00:00:00.00Z");
	
	private PriceRegistry registry;
	
	/**
	 * Read prices from CSV file and insert them into the registry.
	 * 
	 * @throws IOException if price file cannot be read.
	 */
	@BeforeEach
	public void setUp() throws IOException {
		final PriceConfig priceConfig = new PriceConfig("src/test/resources/prices.csv");
		registry = new PriceRegistryImpl(priceConfig);
	}
	
	/**
	 * Happy path test.
	 */
	@Test
	public void test_get_good_prices() {
		assertEquals(Float.valueOf(2.34F), registry.lookup("+381 21 123456", FIRTS_OF_MARCH).get(), "Novi Sad, 1st of March");
		assertEquals(Float.valueOf(3.24F), registry.lookup("+381 21 123456", FIRST_OF_SEP).get(), "Novi Sad, 1st of Sep");
		assertEquals(Float.valueOf(6.0F), registry.lookup("+381 64 123456", FIRTS_OF_MARCH).get(), "MT:S, 1st of March");
	}
	
	/**
	 * Test number not in the list.
	 */
	@Test
	public void test_missing_number() {
		assertFalse(registry.lookup("+44 20 1234567", FIRTS_OF_MARCH).isPresent(), "Wrong number");
	}
	
	/**
	 * Test correct number, but out of time range.
	 */
	@Test
	public void test_out_of_date() {
		final Instant lastYear = Instant.parse("2018-03-01T00:00:00.00Z");
		assertFalse(registry.lookup("+381 21 123456", lastYear).isPresent(), "March, last year");
	}
}
