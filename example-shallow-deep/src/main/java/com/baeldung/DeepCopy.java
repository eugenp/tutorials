package com.baeldung;

import java.util.Arrays;

public class DeepCopy {
    public static void main(String[] args) {
        String[] friends = {"Alice", "Bob", "Charlie"};
        Person originalPerson = new Person("David", friends);
        // Deep Copy
        Person deepCopiedPerson = originalPerson.clone();
        deepCopiedPerson.getFriends()[0] = "Eve";

        System.out.println("Original Person's Friends: " + Arrays.toString(originalPerson.getFriends()));
        System.out.println("Deep Copied Person's Friends: " + Arrays.toString(deepCopiedPerson.getFriends()));
    }
}
