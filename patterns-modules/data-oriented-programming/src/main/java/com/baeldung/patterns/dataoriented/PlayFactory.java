package com.baeldung.patterns.dataoriented;

import com.baeldung.patterns.dataoriented.YahtzeeDO.Strategy;
import com.baeldung.patterns.dataoriented.plays.MoreOfTheSameKind;
import com.baeldung.patterns.dataoriented.plays.Pairs;
import com.baeldung.patterns.dataoriented.plays.PlayedHand;
import com.baeldung.patterns.dataoriented.plays.SpecificDiceValue;

import java.util.List;

public class PlayFactory {
	static PlayedHand createPlayedHand(List<Integer> dices, Strategy strategy) {
		return switch (strategy) {
			case ONES -> new SpecificDiceValue(dices, 1);
			case TWOS -> new SpecificDiceValue(dices, 2);
			case THREES -> new SpecificDiceValue(dices, 3);
			case FOURS -> new SpecificDiceValue(dices, 4);
			case FIVES -> new SpecificDiceValue(dices, 5);
			case SIXES -> new SpecificDiceValue(dices, 6);
			case PAIR -> new Pairs(dices, 1);
			case TWO_PAIRS -> new Pairs(dices, 2);
			case THREE_OF_A_KIND -> new MoreOfTheSameKind(dices, 3);
			case FOUR_OF_A_KIND -> new MoreOfTheSameKind(dices, 4);
		};
	}
}
