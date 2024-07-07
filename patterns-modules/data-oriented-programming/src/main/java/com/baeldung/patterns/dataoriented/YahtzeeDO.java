package com.baeldung.patterns.dataoriented;


import com.baeldung.patterns.dataoriented.plays.Pairs;
import com.baeldung.patterns.dataoriented.plays.PlayedHand;

import java.util.List;

public class YahtzeeDO {

	public enum Strategy {
		ONES, TWOS, THREES, FOURS, FIVES, SIXES, PAIR, TWO_PAIRS, THREE_OF_A_KIND, FOUR_OF_A_KIND;
	}

	public Integer calculateScore(List<Integer> dices, Strategy strategy) {
		PlayedHand hand = PlayFactory.createPlayedHand(dices, strategy);
		return switch (hand) {
			case Pairs p -> ScoringRules.calculatePairs(p.dices(), p.nrOfPairs());
			default -> 0;
		};
	}

}
