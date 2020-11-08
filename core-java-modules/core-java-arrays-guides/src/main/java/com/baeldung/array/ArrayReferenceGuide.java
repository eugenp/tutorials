package com.baeldung.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayReferenceGuide {

    public static void main(String[] args) {
        declaration();

        initialization();

        access();

        iterating();

        varargs();

        transformIntoList();

        transformIntoStream();

        sort();

        search();

        merge();
    }

    private static void declaration() {
        int[] anArray;
        int anotherArray[];
    }

    private static void initialization() {
        int[] anArray = new int[10];
        anArray[0] = 10;
        anArray[5] = 4;

        int[] anotherArray = new int[] {1, 2, 3, 4, 5};
    }

    private static void access() {
        int[] anArray = new int[10];
        anArray[0] = 10;
        anArray[5] = 4;

        System.out.println(anArray[0]);
    }

    private static void iterating() {
        int[] anArray = new int[] {1, 2, 3, 4, 5};
        for (int i = 0; i < anArray.length; i++) {
            System.out.println(anArray[i]);
        }

        for (int element : anArray) {
            System.out.println(element);
        }
    }

    private static void varargs() {
        String[] groceries = new String[] {"Milk", "Tomato", "Chips"};
        varargMethod(groceries);
        varargMethod("Milk", "Tomato", "Chips");
    }

    private static void varargMethod(String... varargs) {
        for (String element : varargs) {
            System.out.println(element);
        }
    }

    private static void transformIntoList() {
        Integer[] anArray = new Integer[] {1, 2, 3, 4, 5};

        // Naïve implementation
        List<Integer> aList = new ArrayList<>(); // We create an empty list
        for (int element : anArray) {
            // We iterate over array's elements and add them to the list
            aList.add(element);
        }

        // Pretty implementation
        aList = Arrays.asList(anArray);

        // Drawbacks
        try {
            aList.remove(0);
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }

        try {
            aList.add(6);
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }

        int[] anotherArray = new int[] {1, 2, 3, 4, 5};
//        List<Integer> anotherList = Arrays.asList(anotherArray);
    }

    private static void transformIntoStream() {
        int[] anArray = new int[] {1, 2, 3, 4, 5};
        IntStream aStream = Arrays.stream(anArray);

        Integer[] anotherArray = new Integer[] {1, 2, 3, 4, 5};
        Stream<Integer> anotherStream = Arrays.stream(anotherArray, 2, 4);
    }

    private static void sort() {
        int[] anArray = new int[] {5, 2, 1, 4, 8};
        Arrays.sort(anArray); // anArray is now {1, 2, 4, 5, 8}

        Integer[] anotherArray = new Integer[] {5, 2, 1, 4, 8};
        Arrays.sort(anotherArray); // anArray is now {1, 2, 4, 5, 8}

        String[] yetAnotherArray = new String[] {"A", "E", "Z", "B", "C"};
        Arrays.sort(yetAnotherArray, 1, 3, Comparator.comparing(String::toString).reversed()); // yetAnotherArray is now {"A", "Z", "E", "B", "C"}
    }

    private static void search() {
        int[] anArray = new int[] {5, 2, 1, 4, 8};
        for (int i = 0; i < anArray.length; i++) {
            if (anArray[i] == 4) {
                System.out.println("Found at index " + i);
                break;
            }
        }

        Arrays.sort(anArray);
        int index = Arrays.binarySearch(anArray, 4);
        System.out.println("Found at index " + index);
    }

    private static void merge() {
        int[] anArray = new int[] {5, 2, 1, 4, 8};
        int[] anotherArray = new int[] {10, 4, 9, 11, 2};

        int[] resultArray = new int[anArray.length + anotherArray.length];
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = (i < anArray.length ? anArray[i] : anotherArray[i - anArray.length]);
        }
        for (int element : resultArray) {
            System.out.println(element);
        }

        Arrays.setAll(resultArray, i -> (i < anArray.length ? anArray[i] : anotherArray[i - anArray.length]));
        for (int element : resultArray) {
            System.out.println(element);
        }
    }

}
