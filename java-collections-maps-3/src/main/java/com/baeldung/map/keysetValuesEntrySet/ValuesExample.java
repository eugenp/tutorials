package com.baeldung.map.keysetValuesEntrySet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class ValuesExample {

    public static void main(String[] args) {

        Map<String, String> hashMap = new HashMap<String, String>() {{
            put("key1", "value1");
            put("key2", "value2");
        }};

        printValues(hashMap);

    }

    public static void printValues(Map<String, String> hashMap) {
        for (String key : hashMap.values()) {
            System.out.println(key);
        }

        Iterator<String> iterator = hashMap.values().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        hashMap.values().iterator().forEachRemaining(System.out::println);

        hashMap.values().stream().forEach(System.out::println);

        Stream.of(hashMap.values().toArray()).forEach(System.out::println);

        System.out.println(hashMap.values().toString());
    }

}
