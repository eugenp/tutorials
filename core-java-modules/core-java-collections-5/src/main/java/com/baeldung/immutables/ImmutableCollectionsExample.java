package com.baeldung.immutables;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ImmutableCollectionsExample {

    public Map<String, String> createUnmodifiableMap(Map<String, String> map) {

        Map<String, String> uMap = Collections.unmodifiableMap(map);
        return uMap;
    }

    public List<String> createUnmodifiableList(List<String> list) {

        List<String> uList = Collections.unmodifiableList(list);
        return uList;
    }

    public Map<String, String> createImmuteableMap() {
        Map<String, String> map = Map.of("color1", "black", "color2", "red", "color3", "orange");
        return map;
    }

    public List<String> createImmuteableList() {
        List<String> list = List.of("One", "Two", "Three");
        return list;
    }

    public List<String> createImmuteableList(List<String> l) {
        List<String> list = List.copyOf(l);
        return list;
    }
}
