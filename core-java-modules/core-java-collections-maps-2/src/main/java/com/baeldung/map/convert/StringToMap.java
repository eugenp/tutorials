package com.baeldung.map.convert;

import com.google.common.base.Splitter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class StringToMap {

    public static Map<String, String> convertWithStream(String mapAsString) {
        Map<String, String> map = Arrays.stream(mapAsString.split(","))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
        return map;
    }

    public static Map<String, String> convertWithGuava(String mapAsString) {
        return Splitter.on(',').withKeyValueSeparator('=').split(mapAsString);
    }
}
