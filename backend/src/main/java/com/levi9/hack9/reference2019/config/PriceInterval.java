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
	public final Instant end;
	public final float price;
	
	/**
	 * @param start
	 * @param end
	 * @param price
	 */
	public PriceInterval(Instant start, Instant end, float price) {
		super();
		this.start = start;
		this.end = end;
		this.price = price;
	}
	
	@Override
	public int compareTo(PriceInterval o) {
		return start.compareTo(o.start);
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
		result = prime * result + ((end == null) ? 0 : end.hashCode());
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
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
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
