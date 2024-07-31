package com.baeldung.concurrentrequest;

public class Stock {
    private final int inStockItems;

    public Stock(int inStockItems) {
        this.inStockItems = inStockItems;
    }

    public int getInStockItems() {
        return inStockItems;
    }
}
