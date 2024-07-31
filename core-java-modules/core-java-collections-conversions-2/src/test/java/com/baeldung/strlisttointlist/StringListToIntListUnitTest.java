package com.baeldung.strlisttointlist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class StringListToIntListUnitTest {
    private final static List<String> STRING_LIST = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
    private final static List<Integer> EXPECTED_LIST = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

    @Test
    void whenUsingStream_thenGetExpectedIntegerList() {
        List<Integer> result = STRING_LIST.stream()
          .map(Integer::valueOf)
          .collect(Collectors.toList());
        assertEquals(EXPECTED_LIST, result);
    }

    @Test
    void whenUsingLoop_thenGetExpectedIntegerList() {
        List<Integer> result = new ArrayList<>(STRING_LIST.size());
        for (String s : STRING_LIST) {
            result.add(Integer.valueOf(s));
        }
        assertEquals(EXPECTED_LIST, result);
    }

    @Test
    void whenUsingGuava_thenGetExpectedIntegerList() {
        List<Integer> result = Lists.transform(STRING_LIST, new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return Integer.valueOf(input);
            }
        });
        assertEquals(EXPECTED_LIST, result);
    }
}