package com.baeldung.hexagonal.ports;

public interface UserRequest {

    public String calculateBestProfitForStock(String stockName);

    public int[] requestStockPrices(String stockName);
}
