package com.baeldung.copying.deep;

import java.util.ArrayList;
import java.util.List;

public class DeepCopyingDemo {
    public static void main(String[] args) {
        DeepCopying original = new DeepCopying();
        original.setName("Marcelo");
        original.setSubjects(new ArrayList<>(List.of("Maths", "Science")));

        try {
            DeepCopying deepCopy = (DeepCopying) original.clone();

            original.getSubjects().add("History");

            System.out.println(original.getSubjects()); // Prints: [Maths, Science, History]
            System.out.println(deepCopy.getSubjects()); // Prints: [Maths, Science]

        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
        }
    }
}