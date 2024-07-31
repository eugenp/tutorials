package com.baeldung.monkey.patching.converter;

public class MoneyConverterImpl implements MoneyConverter {

    private final double conversionRate;

    public MoneyConverterImpl() {
        this.conversionRate = 1.10;
    }

    @Override
    public double convertEURtoUSD(double amount) {
        return amount * conversionRate;
    }
}
