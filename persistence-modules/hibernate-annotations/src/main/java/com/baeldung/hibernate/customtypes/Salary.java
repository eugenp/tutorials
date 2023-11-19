package com.baeldung.hibernate.customtypes;

import java.io.Serializable;
import java.util.Objects;

public class Salary implements Serializable {

    private Long amount;
    private String currency;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salary salary = (Salary) o;
        return Objects.equals(amount, salary.amount) &&
                Objects.equals(currency, salary.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}
