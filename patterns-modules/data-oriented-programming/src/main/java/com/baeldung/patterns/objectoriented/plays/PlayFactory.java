package com.baeldung.patterns.objectoriented.plays;

import com.baeldung.patterns.objectoriented.PlayedHand;
import com.baeldung.patterns.objectoriented.YahtzeeOO.Strategy;

import java.util.List;

public class PlayFactory {
	public static PlayedHand createPlay(Strategy strategy, List<Integer> dices) {
		switch (strategy) {
			case ONES:
				return new SpecificDiceValue(dices, 1);
			case TWOS:
				return new SpecificDiceValue(dices, 2);
			case THREES:
				return new SpecificDiceValue(dices, 3);
			case FOURS:
				return new SpecificDiceValue(dices, 4);
			case FIVES:
				return new SpecificDiceValue(dices, 5);
			case SIXES:
				return new SpecificDiceValue(dices, 6);
			case PAIR:
				return new Pairs(dices, 1);
			case TWO_PAIRS:
				return new Pairs(dices, 2);
			case THREE_OF_A_KIND:
				return new MoreOfTheSameKind(dices, 3);
			case FOUR_OF_A_KIND:
				return new MoreOfTheSameKind(dices, 4);
			default:
				throw new IllegalArgumentException();
		}
	}
}
