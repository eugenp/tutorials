package com.baeldung.linkedlistarray;

import java.util.*;
import java.util.stream.*;

public class LinkedListArray {

    public static int getGroupIndex(int num) {
        return num < 10 ? 0 : num < 20 ? 1 : 2;
    }

    public static LinkedList<Integer>[] createLinkedListArray(int size) {
        @SuppressWarnings("unchecked") LinkedList<Integer>[] array = new LinkedList[size];
        Arrays.setAll(array, i -> new LinkedList<>());
        return array;
    }

    public static List<LinkedList<Integer>> createLinkedListList(int size) {
        return IntStream.range(0, size)
            .mapToObj(i -> new LinkedList<Integer>())
            .collect(Collectors.toList());
    }

    public static LinkedList<Integer>[] groupUsingRawArray(int[] numbers) {
        LinkedList<Integer>[] groupListArray = createLinkedListArray(3);
        for (int num : numbers) {
            groupListArray[getGroupIndex(num)].add(num);
        }
        return groupListArray;
    }

    public static List<LinkedList<Integer>> groupUsingList(int[] numbers) {
        List<LinkedList<Integer>> groupList = createLinkedListList(3);
        for (int num : numbers) {
            groupList.get(getGroupIndex(num))
                .add(num);
        }
        return groupList;
    }

    public static List<LinkedList<Integer>> groupUsingStreams(int[] numbers) {
        List<LinkedList<Integer>> groupList = createLinkedListList(3);
        Arrays.stream(numbers)
            .forEach(num -> groupList.get(getGroupIndex(num))
                .add(num));
        return groupList;
    }

    public static LinkedList<Integer>[] groupUsingSetAll(int[] numbers) {
        LinkedList<Integer>[] groupListArray = createLinkedListArray(3);
        Arrays.stream(numbers)
            .forEach(num -> groupListArray[getGroupIndex(num)].add(num));
        return groupListArray;
    }
}


