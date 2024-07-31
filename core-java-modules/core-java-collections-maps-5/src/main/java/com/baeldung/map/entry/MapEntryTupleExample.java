package com.baeldung.map.entry;

import java.util.*;

public class MapEntryTupleExample {

    public static void main(String[] args) {
        Map.Entry<String, Book> tuple1;
        Map.Entry<String, Book> tuple2;
        Map.Entry<String, Book> tuple3;

        tuple1 = new AbstractMap.SimpleEntry<>("9780134685991", new Book("Effective Java 3d Edition", "Joshua Bloch"));
        tuple2 = new AbstractMap.SimpleEntry<>("9780132350884", new Book("Clean Code", "Robert C Martin"));
        tuple3 = new AbstractMap.SimpleEntry<>("9780132350884", new Book("Clean Code", "Robert C Martin"));

        List<Map.Entry<String, Book>> orderedTuples = new ArrayList<>();
        orderedTuples.add(tuple1);
        orderedTuples.add(tuple2);
        orderedTuples.add(tuple3);

        for (Map.Entry<String, Book> tuple : orderedTuples) {
            System.out.println("key: " + tuple.getKey() + " value: " + tuple.getValue());
        }
    }
}
