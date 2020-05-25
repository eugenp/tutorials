package com.baeldung.postprocessor;

import java.util.Date;

public class StockTrade {

    private final String symbol;
    private final int quantity;
    private final double price;
    private final Date tradeDate;

    private StockTrade(Builder builder) {
        symbol = builder.symbol;
        quantity = builder.quantity;
        price = builder.price;
        tradeDate = builder.tradeDate;
    }

    public static Builder newBuilder() {
        return new Builder();
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

    public static final class Builder {

        private String symbol;
        private int quantity;
        private double price;
        private Date tradeDate;

        private Builder() {
        }

        public Builder withSymbol(String val) {
            symbol = val;
            return this;
        }

        public Builder withQuantity(int val) {
            quantity = val;
            return this;
        }

        public Builder withPrice(double val) {
            price = val;
            return this;
        }

        public Builder withTradeDate(Date val) {
            tradeDate = val;
            return this;
        }

        public StockTrade build() {
            return new StockTrade(this);
        }
    }
}
