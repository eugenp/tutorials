package com.baeldung.jacocoexclusions.service;

public class ProductService {
    private static final double DISCOUNT = 0.25;

    public double getSalePrice(double originalPrice) {
        return originalPrice - originalPrice * DISCOUNT;
    }
}
