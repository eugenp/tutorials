package com.baeldung.hexagon;

public class ConstantTaxRateRepository implements TaxRateRepository {
    private static final double TAX_RATE = 0.13d;

    @Override
    public double getRate() {
        return TAX_RATE;
    }
}
