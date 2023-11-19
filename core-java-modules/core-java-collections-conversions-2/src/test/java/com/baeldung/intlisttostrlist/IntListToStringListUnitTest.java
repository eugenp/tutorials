package com.baeldung.intlisttostrlist;


import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntListToStringListUnitTest {
    private final static List<Integer> INTEGER_LIST = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
    private final static List<String> EXPECTED_LIST = Arrays.asList("1", "2", "3", "4", "5", "6", "7");


    @Test
    void givenAnIntegerList_whenUsingStreamMap_shouldGetExpectedStringList() {
        List<String> result = INTEGER_LIST.stream().map(i -> i.toString()).collect(Collectors.toList());
        assertEquals(EXPECTED_LIST, result);
    }

    @Test
    void givenAnIntegerList_whenUsingGuava_shouldGetExpectedStringList() {
        List<String> result = Lists.transform(INTEGER_LIST, Functions.toStringFunction());
        assertEquals(EXPECTED_LIST, result);
    }

    @Test
    void givenAnIntegerList_whenUsingLoop_shouldGetExpectedStringList() {
        List<String> result = new ArrayList<>();
        for (Integer i : INTEGER_LIST) {
            result.add(i.toString());
        }
        assertEquals(EXPECTED_LIST, result);
    }
}
