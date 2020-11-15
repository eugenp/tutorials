package com.baeldung.collections.comparation;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.*;

class ListVsMapUnitTest {

    @Test
    void givenList_whenIteratingTroughValues_thenEachValueIsPresent() {
        List<String> list = new ArrayList<>();
        list.add("Daniel");
        list.add("Marko");
        for (String name : list) {
            assertThat(name).isIn(list);
        }
    }

    @Test
    void givenMap_whenIteratingTroughValues_thenEachValueIsPresent() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Daniel");
        map.put(2, "Marko");
        for (String name : map.values()) {
            assertThat(name).isIn(map.values());
        }
    }

}
