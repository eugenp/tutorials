package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.core.StockPriceCore;
import com.baeldung.hexagonal.ports.UserRequestPort;
import com.baeldung.hexagonal.repository.StockFetcher;
import com.baeldung.hexagonal.repository.StockRepository;

public class UserRequestPortAdapter implements UserRequestPort {

    private StockPriceCore stockPriceCore;

    public UserRequestPortAdapter() {
        stockPriceCore = new StockPriceCore(new StockAdapter(new StockRepository(new StockFetcher())));
    }

    @Override
    public String calculateBestProfitForStock(String stockName) {
        String message = String.format("Best possible profit for stock \"%s\" is %s", stockName, stockPriceCore.getBestPossibleProfit(stockName));
        return message;
    }

    @Override
    public int[] requestStockPrices(String stockName) {
        return stockPriceCore.requestStockPrices(stockName);
    }

}
