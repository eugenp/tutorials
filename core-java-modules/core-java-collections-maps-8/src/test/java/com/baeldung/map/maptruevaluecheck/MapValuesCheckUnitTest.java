package com.baeldung.map.maptruevaluecheck;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class MapValuesCheckUnitTest {

    @Test
    public void givenMapWithAllTrueValues_whenCheckAllValuesTrue_thenReturnTrue() {
        Map<String, Boolean> map = Map.of("Task1", true, "Task2", true, "Task3", true);

        assertTrue(MapValuesCheck.areAllValuesTrueWithAllMatch(map), "All values should be true");
        assertTrue(MapValuesCheck.areAllValuesTrueWithContains(map), "All values should be true");
        assertTrue(MapValuesCheck.areAllValuesTrueWithReduce(map), "All values should be true");
        assertTrue(MapValuesCheck.areAllValuesTrueWithLoop(map), "All values should be true");
    }

    @Test
    public void givenMapWithSomeFalseValues_whenCheckAllValuesTrue_thenReturnFalse() {
        Map<String, Boolean> map = Map.of("Task1", true, "Task2", false);

        assertFalse(MapValuesCheck.areAllValuesTrueWithAllMatch(map), "Not all values are true");
        assertFalse(MapValuesCheck.areAllValuesTrueWithContains(map), "Not all values are true");
        assertFalse(MapValuesCheck.areAllValuesTrueWithReduce(map), "Not all values are true");
        assertFalse(MapValuesCheck.areAllValuesTrueWithLoop(map), "Not all values are true");
    }

    @Test
    public void givenEmptyMap_whenCheckAllValuesTrue_thenReturnFalseForAllMatchAndTrueForOthers() {
        Map<String, Boolean> map = Map.of();

        assertFalse(MapValuesCheck.areAllValuesTrueWithAllMatch(map), "Empty map should return false");
        assertTrue(MapValuesCheck.areAllValuesTrueWithContains(map), "Empty map should return true");
        assertTrue(MapValuesCheck.areAllValuesTrueWithReduce(map), "Empty map should return true");
    }
}