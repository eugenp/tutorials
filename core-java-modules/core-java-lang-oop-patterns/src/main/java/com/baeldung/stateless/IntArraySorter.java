package com.baeldung.stateless;

public class IntArraySorter {
    
    private SortingStrategy sortingStrategy;
    
    public IntArraySorter(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }
    
    public void setSortingStrategy(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }
    
    public void sort(int[] arr) {
        sortingStrategy.sort(arr);
    }
}
