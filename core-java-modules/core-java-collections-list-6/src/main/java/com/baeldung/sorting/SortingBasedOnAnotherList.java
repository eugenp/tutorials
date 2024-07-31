package com.baeldung.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Ordering;

public class SortingBasedOnAnotherList {
    static List<String> sortUsingForLoop(List<String> listToSort, List<String> listWithOrder) {
        List<String> sortedList = new ArrayList<>();
        for(String element: listWithOrder) {
            if(listToSort.contains(element)) {
                sortedList.add(element);
            }
        }
        return sortedList;
    }

    static void sortUsingComparator(List<String> listToSort, List<String> listWithOrder) {
        listToSort.sort(Comparator.comparingInt(listWithOrder::indexOf));
    }

    static void sortUsingStreamAPI(List<String> listToSort, List<String> listWithOrder) {
        Map<String,Integer> indicesMap = listWithOrder.stream().collect(Collectors.toMap(e->e, listWithOrder::indexOf));
        listToSort.sort(Comparator.comparingInt(indicesMap::get));
    }

    static void sortUsingMap(List<String> listToSort, List<String> listWithOrder) {
        Map<String, Integer> orderedIndicesMap = new HashMap<>();
        for(int i = 0; i < listWithOrder.size(); i++) {
            orderedIndicesMap.put(listWithOrder.get(i), i);
        }
        listToSort.sort(Comparator.comparingInt(orderedIndicesMap::get));
    }

    static List<String> sortUsingGuava(List<String> listToSort, List<String> listWithOrder) {
        Ordering<String> explicitOrdering = Ordering.explicit(listWithOrder);
        List<String> sortedList = explicitOrdering.sortedCopy(listToSort);
        return sortedList;
    }

    static List<String> sortUsingVavr(List<String> listToSort, List<String> listWithOrder) {
        io.vavr.collection.List<String> listWithOrderedElements = io.vavr.collection.List.ofAll(listWithOrder);
        io.vavr.collection.List<String> listToSortElements = io.vavr.collection.List.ofAll(listToSort);
        io.vavr.collection.List<String> sortedList = listToSortElements.sortBy(listWithOrderedElements::indexOf);
        return sortedList.asJava();
    }
}