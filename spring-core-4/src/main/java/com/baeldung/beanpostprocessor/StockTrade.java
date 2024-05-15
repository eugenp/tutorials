package com.baeldung.beanpostprocessor;

import java.util.Date;

public class StockTrade {

    private final String symbol;
    private final int quantity;
    private final double price;
    private final Date tradeDate;

    public StockTrade(String symbol, int quantity, double price, Date tradeDate) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.tradeDate = tradeDate;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getPrice() {
        return this.price;
    }

    public Date getTradeDate() {
        return this.tradeDate;
    }
}
