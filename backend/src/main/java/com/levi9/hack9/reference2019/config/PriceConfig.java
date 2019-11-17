/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.config;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

/**
 * Read CSV configuration file with prices and produce a collection.
 * 
 * @author n.milutinovic
 */
public class PriceConfig {
	private final String csvFile;
	
	/**
	 * Initialize.
	 * 
	 * @param csvFile location of CSV config file, relative to ${CWD}
	 */
	public PriceConfig(final String csvFile) {
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
					final String prefix = record.get("prefix");
					final Instant from = parseTimestamp(record.get("from"));
					final Instant to = parseTimestamp(record.get("to"));
					final float price = Float.parseFloat(record.get("price"));
					return new Price(prefix, from, to, price);
				}).collect(Collectors.toList());
	}
	
	private static Instant parseTimestamp(final String timestamp) {
		LocalDateTime time = LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		return time.toInstant(ZoneOffset.of("Z"));
	}
}
