package com.baeldung.hexagonal;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

public class GuavaSortAdapter implements SortImplementationPort {

    @Override
    public String getName() {
        return "Guava Collections Ordering";
    }

    @Override
    public int[] sort(int[] items) {
        List<Integer> list = Ints.asList(items);
        Collections.sort(list, Ordering.natural());
        
        return list.stream().mapToInt(i->i).toArray();
    }
}
