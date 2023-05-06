package com.baeldung.stateless;

import java.util.List;

public class IntegerListSorter {
    
    private SortingStrategy sortingStrategy;
    
    public IntegerListSorter(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }
    
    public void setSortingStrategy(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }
    
    public List<Integer> sort(List<Integer> arr) {
        return sortingStrategy.sort(arr);
    }
}
