package com.baeldung.map.arraylistinhashmap;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ArrayListMultimap;

public class ArrayListInHashMap {

    public static HashMap<String, ArrayList<String>> addKeyManually(HashMap <String, ArrayList<String>> map, String key, String value) {
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<String>());
        }
        map.get(key).add(value);
        return map;
    }

    public static Map<String, ArrayList<String>> addKeyWithComputeIfAbsent(Map<String, ArrayList<String>> map, String key, String value) {
        map.computeIfAbsent(key, k -> new ArrayList<String>()).add(value);
        return map;
    }

    public static MultiValuedMap<String, String> addKeyToApacheMultiValuedMap(MultiValuedMap<String, String> map, String key, String value) {
        map.put(key, value);
        return map;
    }

    public static MultiValueMap<String, String> addKeyToSpringLinkedMultiValueMap(MultiValueMap<String, String> map, String key, String value) {
        map.add(key, value);
        return map;
    }

    public static Multimap<String, String> addKeyToGuavaMultimap(Multimap<String, String> map, String key, String value) {
        map.put(key, value);
        return map;
    }

}
