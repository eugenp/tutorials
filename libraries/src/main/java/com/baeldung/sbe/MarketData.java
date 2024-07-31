package com.baeldung.sbe;

import java.util.StringJoiner;

import com.baeldung.sbe.stub.Currency;
import com.baeldung.sbe.stub.Market;

public class MarketData {

    private final int amount;
    private final double price;
    private final Market market;
    private final Currency currency;
    private final String symbol;

    public MarketData(int amount, double price, Market market, Currency currency, String symbol) {
        this.amount = amount;
        this.price = price;
        this.market = market;
        this.currency = currency;
        this.symbol = symbol;
    }

    public static class Builder {
        private int amount;

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        private double price;

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        private Market market;

        public Builder market(Market market) {
            this.market = market;
            return this;
        }

        private Currency currency;

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        private String symbol;

        public Builder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public MarketData build() {
            return new MarketData(amount, price, market, currency, symbol);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public Market getMarket() {
        return market;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MarketData.class.getSimpleName() + "[", "]").add("amount=" + amount)
          .add("price=" + price)
          .add("market=" + market)
          .add("currency=" + currency)
          .add("symbol='" + symbol + "'")
          .toString();
    }
}
