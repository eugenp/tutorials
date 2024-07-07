package com.baeldung.patterns.dataoriented;

import java.util.List;
import java.util.Map;

import static java.util.Comparator.reverseOrder;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ScoringRules {
	static int calculatePairs(List<Integer> dices, int nrOfPairs) {
		Map<Integer, Long> frequency = dices.stream()
				.collect(groupingBy(identity(), counting()));

		List<Integer> pairs = frequency
				.entrySet().stream()
				.filter(it -> it.getValue() >= 2)
				.map(Map.Entry::getKey)
				.toList();

		if (pairs.size() < nrOfPairs) {
			return 0;
		}

		return pairs.stream()
				.sorted(reverseOrder())
				.limit(nrOfPairs)
				.mapToInt(it -> it * 2)
				.sum();
	}

}
