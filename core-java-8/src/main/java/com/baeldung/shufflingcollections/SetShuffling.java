package com.baeldung.shufflingcollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SetShuffling {
    public static void main(String[] args) {
        Set<String> students = new HashSet<>();
        students.add("Foo");
        students.add("Bar");
        students.add("Baz");
        students.add("Qux");

        System.out.println("Set before shuffling:");
        System.out.println(students);

        Collections.shuffle(new ArrayList<>(students));
        System.out.println("Set after shuffling:");
        System.out.println(students);
    }
}
