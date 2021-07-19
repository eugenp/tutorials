package com.baeldung.immutableobjects;

public final class Currency {

    private final String value;
    
    private Currency(String currencyValue) {
        value = currencyValue;
    }
    
    public String getValue() {
        return value;
    }
    
    public static Currency of(String value) {
        return new Currency(value);
    }
}
