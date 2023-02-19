package com.baeldung.combine2liststomap;

import static java.lang.Math.min;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class CombineTwoListsInAMapUnitTest {
    private static final List<String> KEY_LIST = Arrays.asList("Number One", "Number Two", "Number Three", "Number Four", "Number Five");
    private static final List<Integer> VALUE_LIST = Arrays.asList(1, 2, 3, 4, 5);
    private static final Map<String, Integer> EXPECTED_MAP = new HashMap<String, Integer>() {{
        put("Number One", 1);
        put("Number Two", 2);
        put("Number Three", 3);
        put("Number Four", 4);
        put("Number Five", 5);
    }};

    @Test
    void givenTwoLists_whenUsingLoopAndListGet_shouldGetExpectedMap() {
        Map<String, Integer> result = new HashMap<>();
        int size = KEY_LIST.size();
        if (KEY_LIST.size() != VALUE_LIST.size()) {
            // throw an exception or print a warning
            size = min(KEY_LIST.size(), VALUE_LIST.size());
        }
        for (int i = 0; i < size; i++) {
            result.put(KEY_LIST.get(i), VALUE_LIST.get(i));
        }
        assertEquals(EXPECTED_MAP, result);
    }

    @Test
    void givenTwoLists_whenUsingStreamApiAndListGet_shouldGetExpectedMap() {
        Map<String, Integer> result = IntStream.range(0, KEY_LIST.size())
          .boxed()
          .collect(Collectors.toMap(KEY_LIST::get, VALUE_LIST::get));
        assertEquals(EXPECTED_MAP, result);
    }

    @Test
    void givenTwoLists_whenUsingIterators_shouldGetExpectedMap() {
        Map<String, Integer> result = new HashMap<>();

        Iterator<String> ik = KEY_LIST.iterator();
        Iterator<Integer> iv = VALUE_LIST.iterator();
        while (ik.hasNext() && iv.hasNext()) {
            result.put(ik.next(), iv.next());
        }

        assertEquals(EXPECTED_MAP, result);
    }

}