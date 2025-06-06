package com.baeldung.covariance;

public class IntegerProducer extends Producer {
    @Override
    public Integer produce(String input) {
        return Integer.parseInt(input);
    }
}
