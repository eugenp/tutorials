package com.baeldung.hexagonal.ports;

public interface UserRequest {
    public void processRequest(String sportName);

    public String calculateBestProfitForStock(String stockName);

    public int[] requestStockPrices(String stockName);
}
