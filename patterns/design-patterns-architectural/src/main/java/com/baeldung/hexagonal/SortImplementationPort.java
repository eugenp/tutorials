package com.baeldung.hexagonal;

public interface SortImplementationPort {

    String getName();

    int[] sort(int[] items);
}
