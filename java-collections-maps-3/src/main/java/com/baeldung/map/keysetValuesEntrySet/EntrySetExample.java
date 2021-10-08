package com.baeldung.map.keysetValuesEntrySet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class EntrySetExample {

    public static void main(String[] args) {
        Map<String, String> hashMap = new HashMap<String, String>() {{
            put("key1", "value1");
            put("key2", "value2");
        }};

        printEntrySet(hashMap);

    }

    public static void printEntrySet(Map<String, String> hashMap) {
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            System.out.println(key + "=" + value);
        }

        Iterator<Map.Entry<String, String>> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        hashMap.entrySet().iterator().forEachRemaining(System.out::println);

        hashMap.entrySet().stream().forEach(System.out::println);

        Stream.of(hashMap.entrySet().toArray()).forEach(System.out::println);

        System.out.println(hashMap.entrySet().toString());
    }

}
