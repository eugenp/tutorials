package com.baeldung.patterns;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.reverseOrder;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ScoringRules {

	private ScoringRules() {
	}

	static int pairs(List<Integer> dices, int nrOfPairs) {
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

	static Integer moreOfSameKind(List<Integer> roll, int nrOfDicesOfSameKind) {
		Map<Integer, Long> frequency = roll.stream()
				.collect(groupingBy(identity(), counting()));

		Optional<Integer> diceValue = frequency.entrySet().stream()
				.filter(entry -> entry.getValue() >= nrOfDicesOfSameKind)
				.map(Map.Entry::getKey)
				.max(Integer::compare);

		return diceValue.map(it -> it * nrOfDicesOfSameKind)
				.orElse(0);
	}

	static Integer specificValue(List<Integer> dices, Integer value) {
		return dices.stream()
				.filter(value::equals)
				.mapToInt(it -> it)
				.sum();
	}
}
