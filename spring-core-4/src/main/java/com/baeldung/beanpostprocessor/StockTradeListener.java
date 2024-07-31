package com.baeldung.beanpostprocessor;

@FunctionalInterface
public interface StockTradeListener {

    void stockTradePublished(StockTrade trade);
}
