package com.baeldung.patterns.objectoriented.plays;

import com.baeldung.patterns.objectoriented.PlayedHand;

import java.util.List;

public class SpecificDiceValue implements PlayedHand {
	private final List<Integer> dices;
	private final Integer diceValue;

	public SpecificDiceValue(List<Integer> dices, int value) {
		this.dices = dices;
		this.diceValue = value;
	}

	@Override
	public int calculateScore() {
		int count = (int) dices.stream().filter(diceValue::equals).count();
		return count * diceValue;
	}
}
