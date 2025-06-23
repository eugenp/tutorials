package com.baeldung.linkedlistarray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LinkedListArray {

    public static LinkedList<Integer>[] groupNumbersUsingRawArray(int[] numbers) {
        @SuppressWarnings("unchecked") LinkedList<Integer>[] groupListArray = new LinkedList[3];

        for (int i = 0; i < groupListArray.length; i++) {
            groupListArray[i] = new LinkedList<>();
        }

        for (int num : numbers) {
            if (num < 10) {
                groupListArray[0].add(num);
            } else if (num < 20) {
                groupListArray[1].add(num);
            } else {
                groupListArray[2].add(num);
            }
        }
        return groupListArray;
    }

    public static List<LinkedList<Integer>> groupNumbersUsingListOfLinkedList(int[] numbers) {
        List<LinkedList<Integer>> groupListArray = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            groupListArray.add(new LinkedList<>());
        }

        for (int num : numbers) {
            if (num < 10) {
                groupListArray.get(0)
                    .add(num);
            } else if (num < 20) {
                groupListArray.get(1)
                    .add(num);
            } else {
                groupListArray.get(2)
                    .add(num);
            }
        }
        return groupListArray;
    }

    public static List<LinkedList<Integer>> groupNumbersUsingStreams(int[] numbers) {
        List<LinkedList<Integer>> groupListArray = IntStream.range(0, 3)
            .mapToObj(i -> new LinkedList<Integer>())
            .collect(Collectors.toList());

        for (int num : numbers) {
            if (num < 10) {
                groupListArray.get(0)
                    .add(num);
            } else if (num < 20) {
                groupListArray.get(1)
                    .add(num);
            } else {
                groupListArray.get(2)
                    .add(num);
            }
        }
        return groupListArray;
    }

    public static LinkedList<Integer>[] groupNumbersUsingSetAll(int[] numbers) {
        @SuppressWarnings("unchecked") LinkedList<Integer>[] groupListArray = new LinkedList[3];
        Arrays.setAll(groupListArray, i -> new LinkedList<>());

        for (int num : numbers) {
            if (num < 10) {
                groupListArray[0].add(num);
            } else if (num < 20) {
                groupListArray[1].add(num);
            } else {
                groupListArray[2].add(num);
            }
        }
        return groupListArray;
    }
}

