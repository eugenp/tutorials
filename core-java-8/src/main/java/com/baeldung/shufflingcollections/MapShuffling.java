package com.baeldung.shufflingcollections;

import java.util.*;

public class MapShuffling {
    public static void main(String[] args) {
        Map<Integer, String> studentsById = new HashMap<>();
        studentsById.put(1, "Foo");
        studentsById.put(2, "Bar");
        studentsById.put(3, "Baz");
        studentsById.put(4, "Qux");

        System.out.println("Students before shuffling:");
        System.out.println(studentsById.values());

        List<Integer> shuffledStudentIds = new ArrayList<>(studentsById.keySet());
        Collections.shuffle(shuffledStudentIds);

        System.out.println("Students after shuffling:");
        for(int id: shuffledStudentIds) {
            System.out.println(studentsById.get(id));
        }
    }
}
