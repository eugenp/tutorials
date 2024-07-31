package com.baeldung.armstrong;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

class ArmstrongNumberUtilUnitTest {
    // @formatter:off
private static final Map<Integer, Boolean> ARMSTRONG_MAP = ImmutableMap.of(
    0, true,
    1, true,
    2, true,
    153, true,
    370, true,
    407, true,
    42, false,
    777, false,
    12345, false);
    // @formatter:on

    private static final List<Integer> A005188_SEQ_1K = ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 153, 370, 371, 407);
    private static final List<Integer> A005188_SEQ_10K = ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 153, 370, 371, 407, 1634, 8208, 9474);

    @Test
    void givenIntegers_whenCheckArmstrong_shouldReturnExpectedResult() {
        ARMSTRONG_MAP.forEach((number, result) -> assertEquals(result, ArmstrongNumberUtil.isArmstrong(number)));
    }

    @Test
    void givenALimit_whenFindArmstrong_shouldReturnExpectedResult() {
        assertEquals(A005188_SEQ_1K, ArmstrongNumberUtil.getA005188Sequence(1000));
        assertEquals(A005188_SEQ_10K, ArmstrongNumberUtil.getA005188Sequence(10000));
    }
}
