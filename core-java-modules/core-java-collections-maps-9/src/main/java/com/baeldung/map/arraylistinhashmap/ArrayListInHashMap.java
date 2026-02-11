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

    public void withoutExternalLibraries() {
        HashMap<String, ArrayList> map = new HashMap<>();
        map.put("key1", new ArrayList<>());
        map.get("key1").add("key1_value1");
        map.get("key1").add("key1_value2");
        map.put("key2", new ArrayList<>());
        map.get("key2").add("key2_value1");
        System.out.println(map);
    }

    public void withoutExternalLibrariesForLoop() {
        HashMap<String, ArrayList> map = new HashMap<>();
        String[] keys = {"key1", "key2"};
        String[][] values = {{"key1_value1", "key1_value2"}, {"key2_value1"}};
        for (int i = 0; i < keys.length; i++) {
            if (!map.containsKey(keys[i])) {
                map.put(keys[i], new ArrayList<>());
            }
            for (String val : values[i]) {
                map.get(keys[i]).add(val);
            }
        }
        System.out.println(map);
    }

    public void withComputeIfAbsent() {
        Map<String, List> map = new HashMap<>();
        map.computeIfAbsent("key1", k -> new ArrayList<>()).add("key1_value1");
        map.computeIfAbsent("key1", k -> new ArrayList<>()).add("key1_value2");
        map.computeIfAbsent("key2", k -> new ArrayList<>()).add("key2_value1");
        System.out.println(map);
    }

    public void withComputeIfAbsentIterables() {
        List<String[]> pairs = Arrays.asList(new String[]{"key1", "key1_value1"}, new String[]{"key1", "key1_value2"}, new String[]{"key2", "key2_value1"});
        Map<String, List> map = new HashMap<>();
        pairs.forEach(pair -> map.computeIfAbsent(pair[0], k -> new ArrayList<>()).add(pair[1]));
        System.out.println(map);
    }

    public void withApacheMultiValuedMap() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key1", "key1_value1");
        map.put("key1", "key1_value2");
        map.put("key2", "key2_value1");
        System.out.println(map);
    }

    public void withSpringLinkedMultiValueMap() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("key1", "key1_value1");
        map.add("key1", "key1_value2");
        map.add("key2", "key2_value1");
        System.out.println(map);
    }

    public void withGuavaMultimap() {
        Multimap<String, String> map = ArrayListMultimap.create();
        map.put("key1", "key1_value1");
        map.put("key1", "key1_value2");
        map.put("key2", "key2_value1");
        System.out.println(map);
    }

}
