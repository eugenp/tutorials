package com.baeldung.hexagonal;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int[] arr = new Random().ints(1000000, Integer.MIN_VALUE, Integer.MAX_VALUE)
            .toArray();

        SortResultRepositoryPort sortResultRepo = new ConsoleSortResultRepositoryAdapter();

        SortImplementationPort guavaSort = new GuavaSortAdapter();
        SortService service1 = new DelegatingSortService(guavaSort, sortResultRepo);

        SimpleSortServiceClientAdapter client1 = new SimpleSortServiceClientAdapter(service1);
        client1.sort(Arrays.copyOf(arr, arr.length));

        SortImplementationPort jdkSort = new JDKSortAdapter();
        SortService service2 = new DelegatingSortService(jdkSort, sortResultRepo);

        SimpleSortServiceClientAdapter client2 = new SimpleSortServiceClientAdapter(service2);
        client2.sort(Arrays.copyOf(arr, arr.length));
    }

}
