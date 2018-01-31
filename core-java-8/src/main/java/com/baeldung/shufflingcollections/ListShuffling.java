package com.baeldung.shufflingcollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListShuffling {
    public static void main(String[] args) {
        List<String> students = new ArrayList<>();
        students.add("Foo");
        students.add("Bar");
        students.add("Baz");
        students.add("Qux");

        System.out.println("List before shuffling:");
        System.out.println(students);

        Collections.shuffle(students);
        System.out.println("List after shuffling:");
        System.out.println(students);
    }
}
