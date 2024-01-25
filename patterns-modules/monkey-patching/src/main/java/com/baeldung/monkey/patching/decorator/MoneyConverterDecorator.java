package com.baeldung.monkey.patching.decorator;

import com.baeldung.monkey.patching.converter.MoneyConverter;

public class MoneyConverterDecorator implements MoneyConverter {

    private final MoneyConverter moneyConverter;

    public MoneyConverterDecorator(MoneyConverter moneyConverter) {
        this.moneyConverter = moneyConverter;
    }

    @Override
    public double convertEURtoUSD(double amount) {

        System.out.println("Before method: convertEURtoUSD");
        double result = moneyConverter.convertEURtoUSD(amount);
        System.out.println("After method: convertEURtoUSD");
        return result;
    }
}
