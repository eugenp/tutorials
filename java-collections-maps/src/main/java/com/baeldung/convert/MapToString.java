package com.baeldung.convert;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.stream.Collectors;

public class MapToString {

    public static String convertWithIteration(Map<Integer, ?> map) {
        StringBuilder mapAsString = new StringBuilder("{");
        for (Integer key : map.keySet()) {
            mapAsString.append(key + "=" + map.get(key) + ", ");
        }
        mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}");
        return mapAsString.toString();
    }

    public static String convertWithStream(Map<Integer, ?> map) {
        String mapAsString = map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
        return mapAsString;
    }

    public static String convertWithGuava(Map<Integer, ?> map) {
        return Joiner.on(",").withKeyValueSeparator("=").join(map);
    }

    public static String convertWithApache(Map map) {
        return StringUtils.join(map);
    }
}
