package com.baeldung.factors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

class FactorsOfIntegerUnitTest {
    //@formatter:off
    private final static Map<Integer, Set<Integer>> FACTOR_MAP = ImmutableMap.of(
    0, ImmutableSet.of(),
    1, ImmutableSet.of(1),
    20, ImmutableSet.of(1, 2, 4, 5, 10, 20),
    24, ImmutableSet.of(1, 2, 3, 4, 6, 8, 12, 24),
    97, ImmutableSet.of(1, 97),
    99, ImmutableSet.of(1, 3, 9, 11, 33, 99),
    100, ImmutableSet.of(1, 2, 4, 5, 10, 20, 25, 50, 100)
    );
    //@formatter:on

    @Test
    void givenAnInteger_whenCallingFindAllFactorsTheDraftVersion_shouldGetExpectedResult() {
        FACTOR_MAP.forEach((number, expected) -> assertEquals(expected, FactorsOfInteger.getAllFactorsVer1(number)));
    }

    @Test
    void givenAnInteger_whenCallingFindAllFactorsVer2_shouldGetExpectedResult() {
        FACTOR_MAP.forEach((number, expected) -> assertEquals(expected, FactorsOfInteger.getAllFactorsVer2(number)));
    }

    @Test
    void givenAnInteger_whenCallingFindAllFactorsVer3_shouldGetExpectedResult() {
        FACTOR_MAP.forEach((number, expected) -> assertEquals(expected, FactorsOfInteger.getAllFactorsVer3(number)));
    }
}
