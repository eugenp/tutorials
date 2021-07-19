package com.baeldung.java9.maps.initialize;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class MapsInitializer {

    @SuppressWarnings("unused")
    public void createMapWithMapOf() {
        Map<String, String> emptyMap = Map.of();
        Map<String, String> singletonMap = Map.of("key1", "value");
        Map<String, String> map = Map.of("key1","value1", "key2", "value2");
    }
    
    public void createMapWithMapEntries() {
        Map<String, String> map = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, String>("name", "John"),
            new AbstractMap.SimpleEntry<String, String>("city", "budapest"),
            new AbstractMap.SimpleEntry<String, String>("zip", "000000"),
            new AbstractMap.SimpleEntry<String, String>("home", "1231231231")
            );
    }
    
    @SuppressWarnings("unused")
    public void createMutableMaps() {
        Map<String, String> map = new HashMap<String, String> (Map.of("key1","value1", "key2", "value2"));
        Map<String, String> map2 = new HashMap<String, String> ( Map.ofEntries(
            new AbstractMap.SimpleEntry<String, String>("name", "John"),
            new AbstractMap.SimpleEntry<String, String>("city", "budapest")));
        
    }
}
