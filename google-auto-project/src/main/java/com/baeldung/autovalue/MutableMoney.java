package com.baeldung.autovalue;

public class MutableMoney {
    @Override
    public String toString() {
        return "MutableMoney [amount=" + amount + ", currency=" + currency + "]";
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private long amount;
    private String currency;

    public MutableMoney(long amount, String currency) {
        super();
        this.amount = amount;
        this.currency = currency;
    }

}
