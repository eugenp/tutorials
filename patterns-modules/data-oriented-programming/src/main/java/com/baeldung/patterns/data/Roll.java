package com.baeldung.patterns.data;

import java.util.Collections;
import java.util.List;

public record Roll(List<Integer> dice, int rollCount) {
	public Roll {
		if (dice.size() != 5)
			throw new IllegalArgumentException("A Roll needs to have exactly 5 dice.");
		if (dice.stream().anyMatch(die -> die < 1 || die > 6))
			throw new IllegalArgumentException("Dice values should be between 1 and 6.");

		dice = Collections.unmodifiableList(dice);
	}
}
