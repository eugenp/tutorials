package com.baeldung.descendingordermap;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.baeldung.descendingordermap.DescendingOrderMap.sortMapByValueDescending;

public class DescendingOrderMapUnitTest {
    @Test
    public void given_UnsortedMap_whenSortingByValueDescending_thenValuesAreInDescendingOrder() {
        Map<String, Integer> unsortedMap = new HashMap<>();
        unsortedMap.put("one", 1);
        unsortedMap.put("three", 3);
        unsortedMap.put("five", 5);
        unsortedMap.put("two", 2);
        unsortedMap.put("four", 4);

        Map<String, Integer> sortedMap = sortMapByValueDescending(unsortedMap);

        Assert.assertEquals(5, sortedMap.size());
        Assert.assertEquals(5, (int) sortedMap.values().iterator().next());

    }
}
