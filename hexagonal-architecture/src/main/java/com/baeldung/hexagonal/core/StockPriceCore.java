package com.baeldung.hexagonal.core;

import com.baeldung.hexagonal.ports.StockPort;
import com.baeldung.hexagonal.ports.UserRequestPort;

/**
 * No framework dependencies
 * Notice that this class implements the Ports end to end.(from left side to right side)
 */
public class StockPriceCore implements UserRequestPort {

    private StockPort stockPort;
    private ProfitCalculator profitCalculator;

    public StockPriceCore(StockPort stockPort) {
        this.stockPort = stockPort;
        profitCalculator = new ProfitCalculator();
    }

    public int getBestPossibleProfit(String stockName) {
        return profitCalculator.maxProfit(stockPort.getStockPrices(stockName));
    }

    @Override
    public String calculateBestProfitForStock(String stockName) {
        Integer profit = profitCalculator.maxProfit(getStockPrices(stockName));
        return profit.toString();
    }

    @Override
    public int[] requestStockPrices(String stockName) {
        return getStockPrices(stockName);
    }

    private int[] getStockPrices(String stockName) {
        return stockPort.getStockPrices(stockName);
    }
}
