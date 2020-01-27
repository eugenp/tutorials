package com.baeldung.hexagonal;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int[] arr = new Random().ints(1000000, Integer.MIN_VALUE, Integer.MAX_VALUE)
            .toArray();

        SortResultRepositoryPort sortResultRepo = new ConsoleSortResultRepositoryAdapter();
        
        SortImplementationPort mergeSort = new MergeSortAdapter();
        SortService service1 = new SortService(mergeSort, sortResultRepo);
        service1.sort(Arrays.copyOf(arr, arr.length));

        SortImplementationPort quickSort = new QuickSortAdapter();
        SortService service2 = new SortService(quickSort, sortResultRepo);
        service2.sort(Arrays.copyOf(arr, arr.length));
    }

}
