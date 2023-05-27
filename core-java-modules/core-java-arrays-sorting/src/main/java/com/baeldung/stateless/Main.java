package com.baeldung.stateless;

import java.util.Arrays;

public class Main {
    
    public static void main(String[] args) {
        int[] array = {17, 6, 11, 41, 5, 3, 4, -9};
        
        IntArraySorter sorter = new IntArraySorter(new BubbleSort());
        sorter.sort(array);
        System.out.println("Bubble Sort: " + Arrays.toString(array));

        array = new int[]{17, 6, 11, 5, 3, 4, -9};
        sorter.setSortingStrategy(new QuickSort());
        sorter.sort(array);
        System.out.println("Quick Sort: " + Arrays.toString(array));
    }
}
