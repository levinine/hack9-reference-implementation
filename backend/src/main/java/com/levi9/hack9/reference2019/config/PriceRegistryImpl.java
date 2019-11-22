/**
 * 
 */
package com.levi9.hack9.reference2019.config;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.levi9.hack9.reference2019.radix.RadixTrie;

/**
 * @author n.milutinovic
 *
 */
public class PriceRegistryImpl implements PriceRegistry {
	private final RadixTrie<SortedSet<PriceInterval>> tree = new RadixTrie<>();
	
	public PriceRegistryImpl(Collection<Price> priceList) {
		Map<String, List<Price>> groupedPrices = priceList.stream().collect(Collectors.groupingBy(price -> price.prefix));
		groupedPrices.forEach((prefix, prices) -> {
			final SortedSet<PriceInterval> priceIntervals = prices.stream()
					.map(price -> new PriceInterval(price.from, price.price))
					.collect(Collectors.toCollection(TreeSet::new));
			tree.put(prefix, priceIntervals);
		});
	}
	@Override
	public Optional<Float> lookup(final String telephone, final Instant timeOfCall) {
		final Predicate<SortedSet<PriceInterval>> callTimeMatcher = getMatcher(timeOfCall);
		return Optional
				.ofNullable(tree.get(telephone, callTimeMatcher))
				.flatMap(intervals -> intervals.stream()
						.filter(getIntervalMatcher(timeOfCall))
						.findFirst())
				.map(interval -> interval.price);
	}

	private static Predicate<SortedSet<PriceInterval>> getMatcher(final Instant time) {
		return priceIntervalSet -> priceIntervalSet.stream().anyMatch(getIntervalMatcher(time));
				
	}
	
	private static Predicate<PriceInterval> getIntervalMatcher(final Instant time) {
		return priceInterval -> time.equals(priceInterval.start) || time.isAfter(priceInterval.start);
	}
}
