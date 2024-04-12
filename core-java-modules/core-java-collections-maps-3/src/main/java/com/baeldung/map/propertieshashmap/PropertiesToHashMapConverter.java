package com.baeldung.map.propertieshashmap;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropertiesToHashMapConverter {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static HashMap<String, String> typeCastConvert(Properties prop) {
        Map step1 = prop;
        Map<String, String> step2 = (Map<String, String>) step1;
        return new HashMap<>(step2);
    }

    public static HashMap<String, String> loopConvert(Properties prop) {
        HashMap<String, String> retMap = new HashMap<>();
        for (Map.Entry<Object, Object> entry : prop.entrySet()) {
            retMap.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
        return retMap;
    }

    public static HashMap<String, String> streamConvert(Properties prop) {
        return prop.entrySet().stream().collect(
                Collectors.toMap(
                  e -> String.valueOf(e.getKey()),
                  e -> String.valueOf(e.getValue()),
                  (prev, next) -> next, HashMap::new
                ));
    }

    public static HashMap<String, String> guavaConvert(Properties prop) {
        return Maps.newHashMap(Maps.fromProperties(prop));
    }
}
