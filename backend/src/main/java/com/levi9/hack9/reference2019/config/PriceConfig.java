/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.config;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Read CSV configuration file with prices and produce a collection.
 * 
 * @author n.milutinovic
 */
@Component
public class PriceConfig {
	private final String csvFile;
	
	/**
	 * Initialize.
	 * 
	 * @param csvFile location of CSV config file, relative to ${CWD}
	 */
	public PriceConfig(@Value("${hack9.configuration.csv}") final String csvFile) {
		this.csvFile = csvFile;
	}
	
	/**
	 * Parse config file and produce a collection of price entries.
	 * 
	 * @return collection of price entries.
	 * @throws IOException in case of file not found or trouble reading.
	 */
	public Collection<Price> getPrices() throws IOException {
		final Reader file = new FileReader(csvFile);
		final CSVParser records = CSVFormat.RFC4180.withHeader().parse(file);
		return StreamSupport.stream(records.spliterator(), false)
				.map(record -> {
					final String prefix = record.get("Prefix");
					final String country = record.get("Country");
					final String city = record.get("City");
					final float price = Float.parseFloat(record.get("Price per min"));
					final Instant start = Instant.parse(record.get("Start"));
					final int initial = Integer.parseInt(record.get("Initial"));
					final int inc = Integer.parseInt(record.get("Increment"));
					return new Price(prefix, country, city, price, start, initial, inc);
				}).collect(Collectors.toList());
	}
}
