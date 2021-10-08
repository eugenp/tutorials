package com.baeldung.map.keysetValuesEntrySet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class KeySetExample {

    public static void main(String[] args) {
        Map<String, String> hashMap = new HashMap<String, String>() {{
            put("key1", "value1");
            put("key2", "value2");
        }};

        printKeySet(hashMap);

    }

    public static void printKeySet(Map<String, String> hashMap) {
        for (String key : hashMap.keySet()) {
            System.out.println(key);
        }

        Iterator<String> iterator = hashMap.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        hashMap.keySet().iterator().forEachRemaining(System.out::println);

        hashMap.keySet().stream().forEach(System.out::println);

        Stream.of(hashMap.keySet().toArray()).forEach(System.out::println);

        System.out.println(hashMap.keySet().toString());
    }

}

