package com.baeldung.stateless;

import java.util.LinkedList;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        List<Integer> list = List.of(17, 6, 11, 5, 3, 4, -9);
        
        List<Integer> modifyableList = new LinkedList<Integer>(list);
        IntegerListSorter sorter = new IntegerListSorter(new BubbleSort());
        List<Integer> sortedList = sorter.sort(modifyableList);
        System.out.println("Bubble Sort: " + sortedList);

        modifyableList = new LinkedList<Integer>(list);
        sorter.setSortingStrategy(new QuickSort());
        sortedList = sorter.sort(modifyableList);
        System.out.println("Quick Sort: " + sortedList);
    }
}
