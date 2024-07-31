package com.baeldung.jsontomap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlattenUtils {
    public static Map<String, Object> flatten(Map<String, Object> map) {
        return flatten(map, null);
    }

    private static Map<String, Object> flatten(Map<String, Object> map, String prefix) {
        Map<String, Object> flatMap = new HashMap<>();
        map.forEach((key, value) -> {
            String newKey = prefix != null ? prefix + "." + key : key;
            if (value instanceof Map) {
                flatMap.putAll(flatten((Map<String, Object>) value, newKey));
            } else if (value instanceof List) {
                // check for list of primitives
                Object element = ((List<?>) value).get(0);
                if (element instanceof String || element instanceof Number || element instanceof Boolean) {
                    flatMap.put(newKey, value);
                } else {
                    // check for list of objects
                    List<Map<String, Object>> list = (List<Map<String, Object>>) value;
                    for (int i = 0; i < list.size(); i++) {
                        flatMap.putAll(flatten(list.get(i), newKey + "[" + i + "]"));
                    }
                }
            } else {
                flatMap.put(newKey, value);
            }
        });
        return flatMap;
    }
}