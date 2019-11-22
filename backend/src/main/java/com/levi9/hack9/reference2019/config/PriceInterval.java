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
	
	/**
	 * @param start
	 * @param end
	 * @param price
	 */
	public PriceInterval(String prefix, Instant start, Instant end, float price) {
		super();
		this.prefix = prefix;
		this.start = start;
		this.end = end;
		this.price = price;
	}
	
	/**
	 * Reverse comparator; later instant comes first.
	 */
	@Override
	public int compareTo(PriceInterval o) {
		return o.start.compareTo(start);
	}

	/**
	 * Eclipse generated.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(end, prefix, price, start);
	}

	/**
	 * Eclipse generated.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PriceInterval))
			return false;
		PriceInterval other = (PriceInterval) obj;
		return Objects.equals(end, other.end) && Objects.equals(prefix, other.prefix)
				&& Float.floatToIntBits(price) == Float.floatToIntBits(other.price)
				&& Objects.equals(start, other.start);
	}
}
