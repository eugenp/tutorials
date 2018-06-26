package com.baeldung;

import java.math.BigDecimal;

public class Quote {

    private String symbol;

    private BigDecimal price;

    public Quote(String symbol, Double price) {
        super();
        this.symbol = symbol;
        this.price = BigDecimal.valueOf(price);
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "[" + symbol + ": " + price.toString() + "]";
    }
}
