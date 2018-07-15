package com.baeldung.property.json.processor;

import org.springframework.util.StringUtils;

import java.util.*;

public class JsonMapProcessor {

    public static Map<String, Object> getFlattenedMap(Map<String, Object> source) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        buildFlattenedMap(result, source, (String)null);
        return result;
    }

    private static void buildFlattenedMap(Map<String, Object> result, Map<String, Object> source,  String path) {
        source.forEach((key, value) -> {
            if (StringUtils.hasText(path)) {
                if (key.startsWith("[")) {
                    key = path + key;
                } else {
                    key = path + '.' + key;
                }
            }

            if (value instanceof String) {
                result.put(key, value);
            } else if (value instanceof Map) {
                Map<String, Object> map = (Map)value;
                buildFlattenedMap(result, map, key);
            } else if (value instanceof Collection) {
                Collection<Object> collection = (Collection)value;
                if (collection.isEmpty()) {
                    result.put(key, "");
                } else {
                    int count = 0;
                    Iterator var7 = collection.iterator();

                    while(var7.hasNext()) {
                        Object object = var7.next();
                        buildFlattenedMap(result, Collections.singletonMap("[" + count++ + "]", object), key);
                    }
                }
            } else {
                result.put(key, value != null ? value : "");
            }

        });
    }

}
