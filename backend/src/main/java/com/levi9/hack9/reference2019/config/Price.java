/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.config;

import java.time.Instant;

/**
 * A price entity, as written in the CSV config file.
 * 
 * @author n.milutinovic
 */
public class Price {
	public final String prefix;
	public final Instant from;
	public final float price;
	
	/**
	 * Case class constructor.
	 * 
	 * @param prefix telephone number prefix.
	 * @param from starting point in time.
	 * @param to ending point in time.
	 * @param price price for the given data point (prefix, period).
	 */
	public Price(String prefix, Instant from, float price) {
		super();
		this.prefix = prefix;
		this.from = from;
		this.price = price;
	}
	
	/**
	 * Eclipse-generated.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + Float.floatToIntBits(price);
		return result;
	}
	
	/**
	 * Eclipse-generated.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Price other = (Price) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		return true;
	}
}
