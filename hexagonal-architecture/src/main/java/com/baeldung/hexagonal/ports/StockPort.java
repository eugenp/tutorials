package com.baeldung.hexagonal.ports;

public interface StockPort {

    public int[] getStockPrices(String stockName);

}
