package com.baeldung.opentelemetry;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String priceNotFound) {
        super(priceNotFound);
    }
}
