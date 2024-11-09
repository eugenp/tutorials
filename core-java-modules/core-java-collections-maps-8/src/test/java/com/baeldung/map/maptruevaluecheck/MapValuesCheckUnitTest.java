package com.baeldung.map.maptruevaluecheck;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class MapValuesCheckUnitTest {

    @Test
    public void givenMapWithAllSameValues_whenCheckAllValuesSame_thenReturnTrue() {
        Map<String, Integer> map = Map.of("English", 100, "Maths", 100, "Science", 100);

        assertTrue(MapValuesCheck.areAllValuesSameWithAllMatch(map));
        assertTrue(MapValuesCheck.areAllValuesSameWithReduce(map));
        assertTrue(MapValuesCheck.areAllValuesSameWithLoop(map));
    }

    @Test
    public void givenMapWithDifferentValues_whenCheckAllValuesSame_thenReturnFalse() {
        Map<String, Integer> map = Map.of("English", 1000, "Maths", 100, "Science", 100);

        assertFalse(MapValuesCheck.areAllValuesSameWithAllMatch(map));
        assertFalse(MapValuesCheck.areAllValuesSameWithReduce(map));
        assertFalse(MapValuesCheck.areAllValuesSameWithLoop(map));
    }

    @Test
    public void givenEmptyMap_whenCheckAllValuesSame_thenReturnTrue() {
        Map<String, Integer> map = Map.of();

        assertTrue(MapValuesCheck.areAllValuesSameWithAllMatch(map));
        assertTrue(MapValuesCheck.areAllValuesSameWithReduce(map));
        assertTrue(MapValuesCheck.areAllValuesSameWithLoop(map));
    }

    @Test
    void givenMapWithAllSameValues_whenCheckingAllValuesSameWithSet_thenReturnTrue() {
        Map<String, Integer> map = Map.of("English", 100, "Maths", 100, "Science", 100);

        assertTrue(MapValuesCheck.areAllValuesSameWithSet(map));
    }

    @Test
    void givenMapWithDifferentValues_whenCheckingAllValuesSameWithSet_thenReturnFalse() {
        Map<String, Integer> map = Map.of("English", 1000, "Maths", 100, "Science", 100);

        assertFalse(MapValuesCheck.areAllValuesSameWithSet(map));
    }
}