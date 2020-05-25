package com.baeldung.postprocessor;

@FunctionalInterface
public interface StockTradeListener {

    void stockTradePublished(StockTrade trade);
}
