package com.baeldung.eclipsecollections;

import static org.junit.Assert.assertEquals;

import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.Assert;
import org.junit.Test;

public class ForEachPatternUnitTest {

    @SuppressWarnings("unchecked")
    @Test
    public void whenInstantiateAndChangeValues_thenCorrect() {
        Pair<Integer, String> pair1 = Tuples.pair(1, "One");
        Pair<Integer, String> pair2 = Tuples.pair(2, "Two");
        Pair<Integer, String> pair3 = Tuples.pair(3, "Three");

        UnifiedMap<Integer, String> map = UnifiedMap.newMapWith(pair1, pair2, pair3);

        for (int i = 0; i < map.size(); i++) {
            map.put(i + 1, "New Value");
        }

        for (int i = 0; i < map.size(); i++) {
            Assert.assertEquals("New Value", map.get(i + 1));
        }
    }
}
