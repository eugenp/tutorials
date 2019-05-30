package com.baeldung.pattern.hexagonal;

interface StockBroker {

    String addOrder(String action, String stockCode, double price, int volume);

}
