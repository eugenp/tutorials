package com.baeldung.testng.utils;

public class Counter {
    private int totalCount;

    public Counter(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void addCounter(int number) {
        this.totalCount = totalCount + number;
    }

    public void subtractCounter(int number) {
        this.totalCount = totalCount - number;
    }

    public void resetTotalCount() {
        this.totalCount = 0;
    }
}
