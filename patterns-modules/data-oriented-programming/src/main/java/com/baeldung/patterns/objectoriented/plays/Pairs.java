package com.baeldung.patterns.objectoriented.plays;

import com.baeldung.patterns.objectoriented.PlayedHand;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Pairs implements PlayedHand {
	private final List<Integer> dices;
	private final int numberOfPairs;

	public Pairs(List<Integer> dices, int expectedNoOfPairs) {
		this.dices = dices;
		this.numberOfPairs = expectedNoOfPairs;
	}

	@Override
	public int calculateScore() {
		Map<Integer, Long> frequency = dices.stream()
				.collect(groupingBy(identity(), counting()));

		List<Integer> pairs = frequency
				.entrySet().stream()
				.filter(it -> it.getValue() >= 2)
				.map(Map.Entry::getKey)
				.toList();

		if (pairs.size() < numberOfPairs) {
			return 0;
		}

		return pairs.stream()
				.sorted(Comparator.reverseOrder())
				.limit(numberOfPairs)
				.mapToInt(it -> it * 2)
				.sum();
	}
}
