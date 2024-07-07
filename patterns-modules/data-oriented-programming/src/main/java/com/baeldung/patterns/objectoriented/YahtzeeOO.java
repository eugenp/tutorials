package com.baeldung.patterns.objectoriented;

import com.baeldung.patterns.objectoriented.plays.PlayFactory;

import java.util.List;

public class YahtzeeOO {

	public enum Strategy {
		ONES, TWOS, THREES, FOURS, FIVES, SIXES, PAIR, TWO_PAIRS, THREE_OF_A_KIND, FOUR_OF_A_KIND;
	}

	public Integer calculateScore(List<Integer> dices, Strategy strategy) {
		PlayedHand rule = PlayFactory.createPlay(strategy, dices);
		return rule.calculateScore();
	}

}
