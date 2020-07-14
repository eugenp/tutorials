package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.core.SportsApp;
import com.baeldung.hexagonal.core.StockPriceCore;
import com.baeldung.hexagonal.ports.FetchSportsRevenue;
import com.baeldung.hexagonal.ports.UserRequest;
import com.baeldung.hexagonal.ports.WriteSportsRevenue;

public class UserRequestAdapter implements UserRequest {

    private SportsApp sportsApp;
    private StockPriceCore stockPriceCore;


    public UserRequestAdapter(FetchSportsRevenue sportsRevenue, WriteSportsRevenue writeSportsRevenue) {
        sportsApp = new SportsApp(sportsRevenue, writeSportsRevenue);
        stockPriceCore = new StockPriceCore();
    }

    @Override
    public void processRequest(String sportName) {
        sportsApp.fetchAndWrite(sportName);
    }

    @Override
    public String requestStock(String stockName) {
        String message = String.format("Best possible profit for stock %s is %d", stockName, stockPriceCore.getBestPossibleProfit(stockName));
        return message;
    }

}
