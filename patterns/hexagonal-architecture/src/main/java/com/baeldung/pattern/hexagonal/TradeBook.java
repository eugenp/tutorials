package com.baeldung.pattern.hexagonal;

class TradeBook {
    private String stockCode;
    private String action;
    private double price;
    private int volume;

    TradeBook(String stockCode, String action, double price, int volume) {
        this.stockCode = stockCode;
        this.action = action;
        this.price = price;
        this.volume = volume;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
