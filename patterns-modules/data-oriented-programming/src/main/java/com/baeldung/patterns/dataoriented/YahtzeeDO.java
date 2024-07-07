package com.baeldung.patterns.dataoriented;


import com.baeldung.patterns.dataoriented.plays.MoreOfTheSameKind;
import com.baeldung.patterns.dataoriented.plays.Pairs;
import com.baeldung.patterns.dataoriented.plays.PlayedHand;
import com.baeldung.patterns.dataoriented.plays.SpecificDiceValue;

import java.util.List;

public class YahtzeeDO {

	public enum Strategy {
		ONES, TWOS, THREES, FOURS, FIVES, SIXES, PAIR, TWO_PAIRS, THREE_OF_A_KIND, FOUR_OF_A_KIND;
	}

	public static int calculateScore(List<Integer> dices, Strategy strategy) {
		return switch (PlayFactory.createPlayedHand(dices, strategy)) {
			case SpecificDiceValue(List<Integer> roll, int value) -> ScoringRules.specificValue(roll, value);
			case Pairs(List<Integer> roll, int pairsCount) -> ScoringRules.pairs(roll, pairsCount);
			case MoreOfTheSameKind(List<Integer> roll, int sameKindCount) -> ScoringRules.moreOfSameKind(roll, sameKindCount);
		};
	}

}
