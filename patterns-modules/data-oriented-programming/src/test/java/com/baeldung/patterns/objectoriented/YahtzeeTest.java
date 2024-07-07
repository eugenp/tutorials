package com.baeldung.patterns.objectoriented;

import com.baeldung.patterns.dataoriented.YahtzeeDO;
import com.baeldung.patterns.objectoriented.YahtzeeOO.Strategy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class YahtzeeTest {

	public static Stream<Arguments> provideTestCases() {
		return Stream.of(
				Arguments.of(List.of(3, 3, 3, 4, 4), "PAIR", 8),
				Arguments.of(List.of(3, 3, 3, 4, 4), "THREE_OF_A_KIND", 9),
				Arguments.of(List.of(1, 2, 2, 4, 4), "PAIR", 8),
				Arguments.of(List.of(1, 2, 2, 2, 5), "THREE_OF_A_KIND", 6),
				Arguments.of(List.of(1, 1, 1, 1, 5), "PAIR", 2),
				Arguments.of(List.of(1, 1, 1, 1, 5), "THREE_OF_A_KIND", 3)
		);
	}

	@ParameterizedTest
	@MethodSource("provideTestCases")
	void testCalculateScore_OO(List<Integer> dices, String strategyStr, Integer expectedScore) {
		YahtzeeOO.Strategy strategy = YahtzeeOO.Strategy.valueOf(strategyStr);

		Integer actualScore = new YahtzeeOO().calculateScore(dices, strategy);

		assertEquals(expectedScore, actualScore,
				() -> String.format("Failed for roll %s and category %s", dices, strategy));
	}

	@ParameterizedTest
	@MethodSource("provideTestCases")
	void testCalculateScore_DO(List<Integer> dices, String strategyStr, Integer expectedScore) {
		YahtzeeDO.Strategy strategy = YahtzeeDO.Strategy.valueOf(strategyStr);

		Integer actualScore = new YahtzeeDO().calculateScore(dices, strategy);

		assertEquals(expectedScore, actualScore,
				() -> String.format("Failed for roll %s and category %s", dices, strategy));
	}

}
