package com.baeldung.hexagon;

public class TestTaxUser {

    public double calculateTax(Double amount) {
        return TaxFactory.getTaxService()
            .calculateTax(amount);
    }
}
