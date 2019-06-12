package com.baeldung.hexagon;

public class TaxCalculator implements TaxService {

    private TaxRateRepository taxRateRepository;

    public TaxCalculator(TaxRateRepository repository) {
        super();
        taxRateRepository = repository;
    }

    @Override
    public double calculateTax(double amount) {
        return amount * taxRateRepository.getRate();
    }
}
