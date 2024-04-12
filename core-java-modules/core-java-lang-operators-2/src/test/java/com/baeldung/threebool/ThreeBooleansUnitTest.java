package com.baeldung.threebool;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ThreeBooleansUnitTest {
    // @formatter:off
    private static final Map<boolean[], Boolean> TEST_CASES_AND_EXPECTED = ImmutableMap.of(
        new boolean[]{true, true, true}, true,
        new boolean[]{true, true, false}, true,
        new boolean[]{true, false, false}, false,
        new boolean[]{false, false, false}, false
        );
    // @formatter:on

    @Test
    void given3Booleans_whenCallingTwoOrMoreAreTrueByLoop_thenGetExpectedResult() {
        TEST_CASES_AND_EXPECTED.forEach((array, expected) -> assertThat(ThreeBooleans.twoOrMoreAreTrueByLoop(array[0], array[1], array[2])).isEqualTo(expected));
    }

    @Test
    void given3Booleans_whenCallingTwoOrMoreAreTrueByCounting_thenGetExpectedResult() {
        TEST_CASES_AND_EXPECTED.forEach((array, expected) -> assertThat(ThreeBooleans.twoOrMoreAreTrueBySum(array[0], array[1], array[2])).isEqualTo(expected));
    }

    @Test
    void given3Booleans_whenCallingTwoOrMoreAreTrueByKarnaughMap_thenGetExpectedResult() {
        TEST_CASES_AND_EXPECTED.forEach((array, expected) -> assertThat(ThreeBooleans.twoorMoreAreTrueByKarnaughMap(array[0], array[1], array[2])).isEqualTo(expected));
    }

    @Test
    void given3Booleans_whenCallingTwoOrMoreAreTrueByOperators_thenGetExpectedResult() {
        TEST_CASES_AND_EXPECTED.forEach((array, expected) -> assertThat(ThreeBooleans.twoOrMoreAreTrueByOperators(array[0], array[1], array[2])).isEqualTo(expected));
    }

    @Test
    void given3Booleans_whenCallingTwoOrMoreAreTrueByXor_thenGetExpectedResult() {
        TEST_CASES_AND_EXPECTED.forEach((array, expected) -> assertThat(ThreeBooleans.twoOrMoreAreTrueByXor(array[0], array[1], array[2])).isEqualTo(expected));
    }

}
