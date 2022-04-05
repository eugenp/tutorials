package com.baeldung.environmentpostprocessor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.environmentpostprocessor.calculator.PriceCalculator;

@Service
public class PriceCalculationService {

    @Autowired
    PriceCalculator priceCalculator;

    public double productTotalPrice(double singlePrice, int quantity) {
        return priceCalculator.calculate(singlePrice, quantity);
    }
}
