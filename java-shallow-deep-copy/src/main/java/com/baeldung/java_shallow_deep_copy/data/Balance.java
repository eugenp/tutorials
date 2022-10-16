package com.baeldung.java_shallow_deep_copy.data;

public class Balance implements Cloneable {

    private float amount;
    private String currency;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Balance(float amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    //Copy constructor
    public Balance(Balance balance) {
        this(balance.getAmount(), balance.getCurrency());
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
