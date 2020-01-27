package com.baeldung.hexagonal;

import java.time.Duration;

public class SortResult {

    private String algo;
    private int[] sortedItems;
    private Duration duration;

    // constructors, getters and setters
    
    public SortResult(String algo, int[] sortedItems, Duration duration) {
        this.algo = algo;
        this.sortedItems = sortedItems;
        this.duration = duration;
    }

    public String getAlgo() {
        return algo;
    }

    public int[] getSortedItems() {
        return sortedItems;
    }

    public Duration getDuration() {
        return duration;
    }
}
