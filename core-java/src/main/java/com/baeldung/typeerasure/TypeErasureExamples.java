package com.baeldung.typeerasure;

import java.util.ArrayList;
import java.util.List;

public class TypeErasureExamples {

    public static void objectArrayErasure() {
        Object[] scores = new Integer[4];
        System.out.println("insert string into integer array");
        scores[0] = "100";
        System.out.printf("scores class: %s", scores.getClass());
    }

    public static void listArrayErasure() {
        List<String> scoresList1 = new ArrayList<String>();
        scoresList1.add("100");
        List<Integer> scoresList2 = new ArrayList<Integer>();
        scoresList2.add(100);
        System.out.printf("scoresList1: %s\n", scoresList1.getClass());
        System.out.printf("scoresList2: %s\n", scoresList2.getClass());
    }

    public static <E> void printArray(E[] array) {
        for (E element : array) {
            System.out.printf("%s ", element);
        }
    }
}
