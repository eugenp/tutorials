package com.baeldung.arrayconvertion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayToListConversion {

    public static void main(String[] args) {
        System.out.println("Array.asList()");
        arrayAsList();
        System.out.println("\nArrayList<>(Arrays.asList())");
        independentArray();
    }

    private static void arrayAsList() {
        String[] stringArray = new String[] { "A", "B", "C", "D" };
        List<String> stringList = Arrays.asList(stringArray);
        System.out.println(stringList); // [A, B, C, D]
        stringList.set(0, "E");
        System.out.println(stringList); // [E, B, C, D]
        System.out.println(Arrays.toString(stringArray)); // [E, B, C, D]
    }

    private static void independentArray() {
        String[] stringArray = new String[] { "A", "B", "C", "D" };
        List<String> stringList = new ArrayList<>(Arrays.asList(stringArray));
        System.out.println(stringList); // [A, B, C, D]
        stringList.set(0, "E");
        System.out.println(stringList); // [E, B, C, D]
        System.out.println(Arrays.toString(stringArray)); // [A, B, C, D]
    }
}