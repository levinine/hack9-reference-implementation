/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.config;

import java.time.Instant;
import java.util.Objects;

/**
 * A price entity, as written in the CSV config file.
 * 
 * @author n.milutinovic
 */
public class Price {
	public final String prefix;
	public final String country;
	public final String city;
	public final Instant start;
	public final float price;
	public final int initial;
	public final int increment;
	
	/**
	 * Case class constructor.
	 * 
	 * @param prefix telephone number prefix.
	 * @param from starting point in time.
	 * @param to ending point in time.
	 * @param price price for the given data point (prefix, period).
	 */
	public Price(String prefix, String country, String city, float price, Instant start, int initial, int increment) {
		super();
		this.prefix = prefix;
		this.country = country;
		this.city = city;
		this.start = start;
		this.price = price;
		this.initial = initial;
		this.increment = increment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, country, increment, initial, prefix, price, start);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Price))
			return false;
		Price other = (Price) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& increment == other.increment && initial == other.initial && Objects.equals(prefix, other.prefix)
				&& Float.floatToIntBits(price) == Float.floatToIntBits(other.price)
				&& Objects.equals(start, other.start);
	}

	
}
