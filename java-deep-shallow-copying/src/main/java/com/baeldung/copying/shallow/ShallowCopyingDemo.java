package com.baeldung.copying.shallow;

import java.util.ArrayList;
import java.util.List;

public class ShallowCopyingDemo {
    public static void main(String[] args) {

        ShallowCopy original = new ShallowCopy();
        original.setName("Alice");
        original.setSubjects(new ArrayList<>(List.of("Physics", "Chemistry")));

        try {

            ShallowCopy shallowCopy = (ShallowCopy) original.clone();

            original.getSubjects().add("Biology");

            System.out.println("Original Subjects: " + original.getSubjects()); // Prints: [Physics, Chemistry, Biology]
            System.out.println("Shallow Copy Subjects: " + shallowCopy.getSubjects()); // Prints: [Physics, Chemistry, Biology]
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
        }
    }
}
