package com.baeldung.java.map;

import java.util.HashMap;
import java.util.Map;

public class KeyCheck {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "A");
        map.put(2, "B");
        map.put(3, null);

        Integer key = 2;
        String value = map.get(key);
        System.out.println(value); //prints "B"

        Integer nonExistentkey = 4;
        value = map.get(nonExistentkey);
        System.out.println(value);  //prints null

        Integer secondKey = 1;
        boolean isSecondKeyPresent = map.containsKey(secondKey);
        System.out.println(isSecondKeyPresent);

        if (map.get(key) != null) {
            System.out.println("the key has non null value");
        } else if (map.containsKey(key) && map.get(key) == null) {
            System.out.println("the key has null value");
        } else {
            System.out.println("the key does not exists");
        }


        if (map.containsKey(key)) { // pattern to avoid
            String foo = map.get(key);
            if (foo != null) {
                System.out.println(foo);
            }
        }

    }
}