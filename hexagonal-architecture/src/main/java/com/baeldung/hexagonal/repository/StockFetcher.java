package com.baeldung.hexagonal.repository;

import java.util.concurrent.ThreadLocalRandom;

/**
 * User: raychenon
 * Date: 14/7/2020
 * Mock to fetch stock prices from the last N days
 */
public class StockFetcher {

    private int SIZE = 10; // last N days
    private int MAX_PRICE = 100;

    /**
     * In reality, this method is supposed to fetch from an API.
     * Here, the data are mocked by random values.
     */
    public int[] getStock(String stockName) {
        int[] stockPrices = new int[SIZE];
        ThreadLocalRandom rdn = ThreadLocalRandom.current();
        for (int i = 0; i < SIZE; i++) {
            stockPrices[i] = rdn.nextInt(0, MAX_PRICE + 1);
        }
        return stockPrices;
    }
}
