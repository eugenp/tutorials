package com.baeldung.hexagonal.ports;

/**
 * User: raychenon
 * Date: 12/7/2020
 */
public interface StockPort {

    public int[] getStockPrices(String stockName);

}
