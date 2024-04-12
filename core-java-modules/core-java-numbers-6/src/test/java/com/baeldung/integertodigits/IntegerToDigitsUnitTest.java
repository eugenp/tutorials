package com.baeldung.integertodigits;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegerToDigitsUnitTest {
    private final static int THE_NUMBER = 1230456;
    private final static List<Integer> EXPECTED_INT_LIST = Lists.newArrayList(1, 2, 3, 0, 4, 5, 6);
    private final static String[] EXPECTED_STR_ARRAY = new String[] { "1", "2", "3", "0", "4", "5", "6" };
    private final static List<String> EXPECTED_STR_LIST = Lists.newArrayList("1", "2", "3", "0", "4", "5", "6");
    private final static char[] EXPECTED_CHAR_ARRAY = new char[] { '1', '2', '3', '0', '4', '5', '6' };

    @Test
    void whenUsingModOperator_thenGetExpectedResult() {
        int number = THE_NUMBER;
        LinkedList<Integer> result = new LinkedList<>();
        while (number > 0) {
            result.push(number % 10);
            number /= 10;
        }
        assertEquals(EXPECTED_INT_LIST, result);
    }


    private void collectDigits(int num, List<Integer> digitList) {
        if (num / 10 > 0) {
            collectDigits(num / 10, digitList);
        }
        digitList.add(num % 10);
    }

    @Test
    void whenUsingModOperatorAndRecursion_thenGetExpectedResult() {
        List<Integer> result = new ArrayList<>();
        collectDigits(THE_NUMBER, result);
        assertEquals(EXPECTED_INT_LIST, result);
    }

    @Test
    void whenUsingIntStream_thenGetExpectedResult() {
        String numStr = String.valueOf(THE_NUMBER);
        List<Integer> result = numStr.chars().map(Character::getNumericValue).boxed().collect(Collectors.toList());
        assertEquals(EXPECTED_INT_LIST, result);
    }

    @Test
    void whenUsingToCharArray_thenGetExpectedResult() {
        String numStr = String.valueOf(THE_NUMBER);
        char[] result = numStr.toCharArray();
        assertArrayEquals(EXPECTED_CHAR_ARRAY, result);
    }

    @Test
    void whenUsingSplit_thenGetExpectedResult() {
        String numStr = String.valueOf(THE_NUMBER);
        String[] result = numStr.split("(?<=.)");
        assertArrayEquals(EXPECTED_STR_ARRAY, result);
    }
}