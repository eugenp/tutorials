package com.baeldung.map.multivaluedmap;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import java.util.ArrayList;
import java.util.List;

// Example class for MultivaluedMap.
class MultivaluedMapExample {
    public static void main(String[] args) {
        MultivaluedMap<String, List<Integer>> mulmap = new MultivaluedHashMap<>();
        List<Integer> list = new ArrayList<>();

        // Adding values to the list.
        list.add(1);
        list.add(2);
        list.add(3);

        // Mapping keys to values.
        mulmap.add("first", list);
        mulmap.add(null, null);

        // Printing values for key "first".
        System.out.println("Values for key 'first': " + mulmap.get("first"));
    }
}
