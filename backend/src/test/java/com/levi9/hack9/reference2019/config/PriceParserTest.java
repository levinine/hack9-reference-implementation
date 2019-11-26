/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.config;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.Instant;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test CSV parsing of prices file.
 * 
 * @author n.milutinovic
 */
class PriceParserTest {
	private PriceConfig priceParser;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		priceParser = new PriceConfig("src/test/resources/prices.csv");
	}

	/**
	 * Test method for {@link com.levi9.hack9.reference2019.config.PriceConfig#getPrices()}.
	 * @throws IOException 
	 */
	@Test
	final void test_get_prices() throws IOException {
		Collection<Price> result = priceParser.getPrices();
		assertEquals(6, result.size(), "Expect 6 prices");
	}
	
	/**
	 * Test if the first price is read correctly.
	 * 
	 * @throws IOException
	 */
	@Test
	final void test_first() throws IOException {
		Collection<Price> result = priceParser.getPrices();
		Price first = result.stream().findFirst().get();
		assertEquals("+38121", first.prefix, "Prefix");
		assertEquals(Instant.parse("2019-01-01T00:00:00.00Z"), first.start, "From");
		assertEquals(2.34F, first.price, "Price");
	}
}
