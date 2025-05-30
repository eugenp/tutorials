package com.baeldung.convertlisttomap;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class ListToMapUnitTest {

    private ListToMapConverter converter;
    private List<String> source;

    @Before
    public void setUp() {
        converter = new ListToMapConverter();
        source = Arrays.asList("List", "Map", "Set", "Tree");
    }

    @Test
    public void givenAList_whenConvertWithJava8GroupBy_thenReturnMap() {
        Map<Integer, List<String>> convertedMap = converter.groupingByStringLength(source, HashMap::new, ArrayList::new);
        assertTrue(convertedMap.get(3)
            .contains("Map"));
    }

    @Test
    public void givenAList_whenConvertWithJava8Collect_thenReturnMap() {
        Map<Integer, List<String>> convertedMap = converter.streamCollectByStringLength(source, HashMap::new, ArrayList::new);
        assertTrue(convertedMap.get(3)
            .contains("Map"));
    }

    @Test
    public void givenAList_whenConvertWithCollectorToMap_thenReturnMap() {
        Map<Integer, List<String>> convertedMap = converter.collectorToMapByStringLength(source, HashMap::new, ArrayList::new);
        assertTrue(convertedMap.get(3)
            .contains("Map"));
    }

}
