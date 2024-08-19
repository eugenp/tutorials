package com.baeldung.patterns;

import com.baeldung.patterns.data.Roll;
import com.baeldung.patterns.data.Turn;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.baeldung.patterns.Yahtzee.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class YahtzeeTest {

	public static Stream<Arguments> whenThePlayerChoosesAStrategy_thenCalculateCorrectScore() {
		return Stream.of(
				Arguments.of(List.of(3, 3, 3, 4, 4), "ONE_PAIR", 8),
				Arguments.of(List.of(3, 3, 3, 4, 4), "THREE_OF_A_KIND", 9),
				Arguments.of(List.of(1, 2, 2, 4, 4), "ONE_PAIR", 8),
				Arguments.of(List.of(1, 2, 2, 2, 5), "THREE_OF_A_KIND", 6),
				Arguments.of(List.of(1, 1, 1, 1, 5), "ONE_PAIR", 2),
				Arguments.of(List.of(1, 1, 1, 1, 5), "THREE_OF_A_KIND", 3)
		);
	}

	@ParameterizedTest
	@MethodSource
	void whenThePlayerChoosesAStrategy_thenCalculateCorrectScore(List<Integer> dices, String strategyStr, Integer expectedScore) {
		enqueueFakeDiceValues(dices);

		Roll roll = roll();
		Turn play = chooseStrategy(roll, strategyStr);
		int score = score(play);

		assertEquals(expectedScore, score);
	}

	@Test
	void whenThePlayerRerollsAndChoosesTwoPairs_thenCalculateCorrectScore() {
		enqueueFakeDiceValues(1, 1, 2, 2, 3, 5, 5);

		Roll roll = roll(); // => { dice: [1,1,2,2,3] }
		roll = rerollValues(roll, 1, 1); // => { dice: [5,5,2,2,3] }
		Turn turn = chooseStrategy(roll, "TWO_PAIRS");
		int score = score(turn);

		assertEquals(14, score);
	}

	private static void enqueueFakeDiceValues(List<Integer> values) {
		Yahtzee.diceValueGenerator = values.iterator()::next;
	}

	private static void enqueueFakeDiceValues(Integer... values) {
		enqueueFakeDiceValues(List.of(values));
	}

}
