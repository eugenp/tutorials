package com.baeldung.patterns.objectoriented.plays;

import com.baeldung.patterns.objectoriented.PlayedHand;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class MoreOfTheSameKind implements PlayedHand {
	private final List<Integer> dices;
	private final int nrOfDicesOfSameKind;

	public MoreOfTheSameKind(List<Integer> dices, int diceValue) {
		this.dices = dices;
		this.nrOfDicesOfSameKind = diceValue;
	}

	@Override
	public int calculateScore() {
		Map<Integer, Long> frequency = dices.stream()
				.collect(groupingBy(identity(), counting()));

		Optional<Integer> diceValue = frequency.entrySet().stream()
				.filter(entry -> entry.getValue() >= nrOfDicesOfSameKind)
				.map(Map.Entry::getKey)
				.max(Integer::compare);

		return diceValue.map(it -> it * nrOfDicesOfSameKind)
				.orElse(0);
	}
}
