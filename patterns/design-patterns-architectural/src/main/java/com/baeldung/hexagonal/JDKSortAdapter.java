package com.baeldung.hexagonal;

import java.util.Arrays;

public class JDKSortAdapter implements SortImplementationPort {

    @Override
    public String getName() {
        return "JDK Default Sort";
    }

    @Override
    public int[] sort(int[] items) {
        Arrays.sort(items);
        return items;
    }
}
