package com.baeldung.pattern.hexagonal;

interface StockTradeBookPersistence {

    boolean add(String stockCode, String action, double price, int volume);

    TradeBook get(String stockCode, String action, double price);

}
