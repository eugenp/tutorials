package com.baeldung.hexagonal;

public class SimpleSortServiceClientAdapter extends SortServiceClientPort {

    public SimpleSortServiceClientAdapter(SortService sortService) {
        super(sortService);
    }
    
    public int[] sort(int[] items) {
        return getService().sort(items);
    }
}
