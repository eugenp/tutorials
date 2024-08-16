package com.baeldung.cloning;

import java.math.BigDecimal;
import java.util.Objects;

public class Currency {

    private String code;
    private String symbol;
    private BigDecimal value;

    public Currency(String code, String symbol, BigDecimal value) {
        this.code = code;
        this.symbol = symbol;
        this.value = value;
    }

    public Currency(Currency that) {
        this(that.code, that.symbol, that.value);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Currency)) {
            return false;
        }
        Currency currency = (Currency) o;
        return Objects.equals(getCode(), currency.getCode()) && Objects.equals(getSymbol(), currency.getSymbol()) &&
            Objects.equals(getValue(), currency.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getSymbol(), getValue());
    }


}
