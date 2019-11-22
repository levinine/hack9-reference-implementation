/**
 * 
 */
package com.levi9.hack9.reference2019.config;

import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.levi9.hack9.reference2019.radix.RadixTrie;

/**
 * @author n.milutinovic
 *
 */
@Service
public class PriceRegistryImpl implements PriceRegistry {
	private static final Instant MAX_FUTURE = Instant.parse("2030-12-31T23:59:59.00Z");
	
	private final RadixTrie<SortedSet<PriceInterval>> tree = new RadixTrie<>();
	
	public PriceRegistryImpl(PriceConfig priceConfig) throws IOException {
		Collection<Price> priceList = priceConfig.getPrices();
		Map<String, List<Price>> groupedPrices = priceList.stream().collect(Collectors.groupingBy(price -> price.prefix));
		groupedPrices.forEach((prefix, prices) -> {
			final SortedSet<PriceInterval> priceIntervals = prices.stream()
					.map(price -> new PriceInterval(price.prefix, price.from, null, price.price))
					.collect(Collectors.toCollection(TreeSet::new));
			tree.put(prefix, priceIntervals);
		});
	}
	@Override
	public Optional<PriceInterval> lookup(final String telephone, final Instant timeOfCall) {
		final Predicate<SortedSet<PriceInterval>> callTimeMatcher = getMatcher(timeOfCall);
		return Optional
				.ofNullable(tree.get(telephone, callTimeMatcher))
				.flatMap(intervals -> {
					final List<PriceInterval> slice = intervals.stream()
							.filter(getIntervalMatcher(timeOfCall))
							.limit(2)
							.collect(Collectors.toList());
					if (slice.size() == 0) {
						return Optional.empty();
					} else {
						final PriceInterval first = slice.get(0);
						final Instant end = (slice.size() == 1) ? MAX_FUTURE : slice.get(1).start;
						return Optional.of(new PriceInterval(first.prefix, first.start, end, first.price));
					}
				});
	}

	private static Predicate<SortedSet<PriceInterval>> getMatcher(final Instant time) {
		return priceIntervalSet -> priceIntervalSet.stream().anyMatch(getIntervalMatcher(time));
				
	}
	
	private static Predicate<PriceInterval> getIntervalMatcher(final Instant time) {
		return priceInterval -> time.equals(priceInterval.start) || time.isAfter(priceInterval.start);
	}
}
