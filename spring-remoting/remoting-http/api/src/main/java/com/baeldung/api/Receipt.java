package com.baeldung.api;

import java.io.Serializable;
import java.text.NumberFormat;

public class Receipt implements Serializable {

    private int totalAmountInCents;

    public Receipt(int totalAmountInCents) {
        this.totalAmountInCents = totalAmountInCents;
    }

    @Override public String toString() {
        return "Total amount: " + NumberFormat.getCurrencyInstance().format(totalAmountInCents/100);
    }
}
