package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.core.SportsApp;
import com.baeldung.hexagonal.core.StockPriceCore;
import com.baeldung.hexagonal.ports.UserRequest;

public class UserRequestAdapter implements UserRequest {

    private SportsApp sportsApp;
    private StockPriceCore stockPriceCore;


    public UserRequestAdapter() {
        stockPriceCore = new StockPriceCore();
    }

    @Override
    public void processRequest(String sportName) {
        sportsApp.fetchAndWrite(sportName);
    }

    @Override
    public String calculateBestProfitForStock(String stockName) {
        String message = String.format("Best possible profit for stock \"%s\" is %d", stockName, stockPriceCore.getBestPossibleProfit(stockName));
        return message;
    }

    @Override
    public int[] requestStockPrices(String stockName) {
        return stockPriceCore.getStockPrices(stockName);
    }

}
