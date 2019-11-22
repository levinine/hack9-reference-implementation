/**
 * 
 */
package com.levi9.hack9.reference2019.config;

import java.time.Instant;

/**
 * @author n.milutinovic
 *
 */
public final class PriceInterval implements Comparable<PriceInterval> {
	public final Instant start;
	public final float price;
	
	/**
	 * @param start
	 * @param end
	 * @param price
	 */
	public PriceInterval(Instant start, float price) {
		super();
		this.start = start;
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
	 * 
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(price);
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}
	
	/**
	 * Eclipse generated.
	 * 
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PriceInterval other = (PriceInterval) obj;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}
}
