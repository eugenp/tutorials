package com.baeldung.linkedlistarray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class LinkedListArray {

    public static void allocateNumbers(int[] numbers, ArrayList<LinkedList<Integer>> groups) {
        for (int num : numbers) {
            int index = (num < 10) ? 0 : (num < 20 ? 1 : 2);
            groups.get(index)
                .add(num);
        }
    }

    public static void allocateNumbers(int[] numbers, LinkedList<Integer>[] groups) {
        for (int num : numbers) {
            int index = (num < 10) ? 0 : (num < 20 ? 1 : 2);
            groups[index].add(num);
        }
    }

    public static LinkedList<Integer>[] createUsingRawArray() {
        @SuppressWarnings("unchecked") LinkedList<Integer>[] groups = new LinkedList[3];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = new LinkedList<>();
        }
        return groups;
    }

    public static ArrayList<LinkedList<Integer>> createUsingArrayList() {
        ArrayList<LinkedList<Integer>> groups = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            groups.add(new LinkedList<>());
        }
        return groups;
    }

    public static ArrayList<LinkedList<Integer>> createUsingStreams() {
        ArrayList<LinkedList<Integer>> groups = new ArrayList<>();
        IntStream.range(0, 3)
            .forEach(i -> groups.add(new LinkedList<>()));
        return groups;
    }

    public static LinkedList<Integer>[] createUsingSetAll() {
        @SuppressWarnings("unchecked") LinkedList<Integer>[] groups = new LinkedList[3];
        Arrays.setAll(groups, i -> new LinkedList<>());
        return groups;
    }
}

