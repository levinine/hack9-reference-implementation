/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * Time related utilities.
 * 
 * @author n.milutinovic
 */
public class TimeUtil {

	/**
	 * No construction, this is ustility class.
	 */
	private TimeUtil() {
		// Nothing to do.
	}

	/**
	 * Convert DB timestamp to {@link OffsetDateTime}.
	 * 
	 * @param timestamp timestamp to convert.
	 * @return offset date time in UTC timezone.
	 */
	public static OffsetDateTime convert(Timestamp timestamp) {
		return OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), ZoneId.of("UTC"));
	}
}
