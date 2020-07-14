package com.baeldung.hexagonal.ports;

public interface UserRequestPort {

    public String calculateBestProfitForStock(String stockName);

    public int[] requestStockPrices(String stockName);
}
