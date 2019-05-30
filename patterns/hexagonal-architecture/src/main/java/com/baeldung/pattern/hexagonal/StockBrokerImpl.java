package com.baeldung.pattern.hexagonal;

public class StockBrokerImpl implements StockBroker {

    private StockTradeBookPersistence persistence;

    public String addOrder(String stockCode, String action, double price, int volume) {
        String statusCode = "0";

        TradeBook tradeBook = persistence.get(stockCode, action, price);
        if (tradeBook != null) {
            // if there's a trade book entry, increase the volume
            tradeBook.setVolume(tradeBook.getVolume() + volume);
        } else {
            // if not found, then create a new trade book
            persistence.add(stockCode, action, price, volume);
            statusCode = "1";
        }
        return statusCode;
    }

    void setPersistence(StockTradeBookPersistence persistence) {
        this.persistence = persistence;
    }
}
