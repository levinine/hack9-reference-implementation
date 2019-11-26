/**
 * 
 */
package com.levi9.hack9.reference2019.config;

import java.time.Instant;
import java.util.Objects;

/**
 * @author n.milutinovic
 *
 */
public final class PriceInterval implements Comparable<PriceInterval> {
	public final String prefix;
	public final Instant start;
	public final Instant end;
	public final float price;
	public final int initial;
	public final int increment;
	
	/**
	 * @param start
	 * @param end
	 * @param price
	 */
	public PriceInterval(String prefix, Instant start, Instant end, float price, int initial, int increment) {
		super();
		this.prefix = prefix;
		this.start = start;
		this.end = end;
		this.price = price;
		this.initial = initial;
		this.increment = increment;
	}
	
	/**
	 * Reverse comparator; later instant comes first.
	 */
	@Override
	public int compareTo(PriceInterval o) {
		return o.start.compareTo(start);
	}

	@Override
	public int hashCode() {
		return Objects.hash(end, increment, initial, prefix, price, start);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PriceInterval))
			return false;
		PriceInterval other = (PriceInterval) obj;
		return Objects.equals(end, other.end) && increment == other.increment && initial == other.initial
				&& Objects.equals(prefix, other.prefix)
				&& Float.floatToIntBits(price) == Float.floatToIntBits(other.price)
				&& Objects.equals(start, other.start);
	}

	
}
