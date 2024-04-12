package com.baeldung.map.keysetValuesEntrySet;

import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ValuesExampleUnitTest {

    @Test
    public void givenHashMap_whenValuesApplied_thenShouldReturnCollectionOfValues() {
        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);

        Collection<Integer> actualValues = map.values();

        assertEquals(2, actualValues.size());
        assertTrue(actualValues.contains(1));
        assertTrue(actualValues.contains(2));
    }

}
