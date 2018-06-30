package com.baeldung;

public class Quote {

    private String symbol;

    private Double price;

    public Quote(String symbol, Double price) {
        super();
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "[" + symbol + ": " + price.toString() + "]";
    }
}
