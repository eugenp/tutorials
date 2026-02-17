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

    public HashMap<String, ArrayList<String>> withoutExternalLibraries() {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        map.put("key1", new ArrayList<String>());
        map.get("key1").add("key1_value1");
        map.get("key1").add("key1_value2");
        map.put("key2", new ArrayList<String>());
        map.get("key2").add("key2_value1");
        return map;
    }

    public HashMap<String, ArrayList<String>>  withoutExternalLibrariesForLoop() {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        String[] keys = {"key1", "key2"};
        String[][] values = {{"key1_value1", "key1_value2"}, {"key2_value1"}};
        for (int i = 0; i < keys.length; i++) {
            if (!map.containsKey(keys[i])) {
                map.put(keys[i], new ArrayList<String>());
            }
            for (String val : values[i]) {
                map.get(keys[i]).add(val);
            }
        }
        return map;
    }

    public Map<String, ArrayList<String>> withComputeIfAbsent() {
        Map<String, ArrayList<String>> map = new HashMap<>();
        map.computeIfAbsent("key1", k -> new ArrayList<String>()).add("key1_value1");
        map.computeIfAbsent("key1", k -> new ArrayList<String>()).add("key1_value2");
        map.computeIfAbsent("key2", k -> new ArrayList<String>()).add("key2_value1");
        return map;
    }

    public Map<String, ArrayList<String>> withComputeIfAbsentIterables() {
        List<String[]> pairs = Arrays.asList(new String[]{"key1", "key1_value1"}, new String[]{"key1", "key1_value2"}, new String[]{"key2", "key2_value1"});
        Map<String, ArrayList<String>> map = new HashMap<>();
        pairs.forEach(pair -> map.computeIfAbsent(pair[0], k -> new ArrayList<String>()).add(pair[1]));
        return map;
    }

    public MultiValuedMap<String, String> withApacheMultiValuedMap() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key1", "key1_value1");
        map.put("key1", "key1_value2");
        map.put("key2", "key2_value1");
        return map;
    }

    public MultiValueMap<String, String> withSpringLinkedMultiValueMap() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("key1", "key1_value1");
        map.add("key1", "key1_value2");
        map.add("key2", "key2_value1");
        return map;
    }

    public Multimap<String, String> withGuavaMultimap() {
        Multimap<String, String> map = ArrayListMultimap.create();
        map.put("key1", "key1_value1");
        map.put("key1", "key1_value2");
        map.put("key2", "key2_value1");
        return map;
    }

}
