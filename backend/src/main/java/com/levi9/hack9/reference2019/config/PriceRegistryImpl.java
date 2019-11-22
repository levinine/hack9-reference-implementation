/**
 * 
 */
package com.levi9.hack9.reference2019.config;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.levi9.hack9.reference2019.radix.RadixTrie;

/**
 * @author n.milutinovic
 *
 */
public class PriceRegistryImpl implements PriceRegistry {
	private final RadixTrie<Set<PriceInterval>> tree = new RadixTrie<>();
	
	public PriceRegistryImpl(Collection<Price> priceList) {
		Map<String, List<Price>> groupedPrices = priceList.stream().collect(Collectors.groupingBy(price -> price.prefix));
		groupedPrices.forEach((prefix, prices) -> {
			final Set<PriceInterval> priceIntervals = prices.stream()
					.map(price -> new PriceInterval(price.from, price.to, price.price))
					.collect(Collectors.toSet());
			tree.put(prefix, priceIntervals);
		});
	}
	@Override
	public Optional<Float> lookup(final String telephone, final Instant timeOfCall) {
		final Predicate<Set<PriceInterval>> callTimeMatcher = getMatcher(timeOfCall);
		return Optional
				.ofNullable(tree.get(telephone, callTimeMatcher))
				.flatMap(intervals -> intervals.stream()
						.filter(getIntervalMatcher(timeOfCall))
						.findFirst())
				.map(interval -> interval.price);
	}

	private static Predicate<Set<PriceInterval>> getMatcher(final Instant time) {
		return priceIntervalSet -> priceIntervalSet.stream().anyMatch(getIntervalMatcher(time));
				
	}
	
	private static Predicate<PriceInterval> getIntervalMatcher(final Instant time) {
		return priceInterval -> (time.equals(priceInterval.start) || time.isAfter(priceInterval.start))
				&& (time.equals(priceInterval.end) || time.isBefore(priceInterval.end));
	}
}
