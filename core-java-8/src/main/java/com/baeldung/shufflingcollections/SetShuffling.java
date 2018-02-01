package com.baeldung.shufflingcollections;

import java.util.*;

public class SetShuffling {
    public static void main(String[] args) {
        Set<String> students = new HashSet<>();
        students.add("Foo");
        students.add("Bar");
        students.add("Baz");
        students.add("Qux");

        System.out.println("Set before shuffling:");
        System.out.println(students);

        List<String> studentList = new ArrayList<>(students);

        Collections.shuffle(studentList);
        System.out.println("Students after shuffling:");
        System.out.println(studentList);
    }
}
