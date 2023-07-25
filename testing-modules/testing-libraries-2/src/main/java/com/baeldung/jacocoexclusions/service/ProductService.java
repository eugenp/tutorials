package com.baeldung.jacocoexclusions.service;

public class ProductService {
    private static final double DISCOUNT = 0.25;

public double getSalePrice(double originalPrice, boolean flag) {
    double discount;
    if (flag) {
        discount = originalPrice - originalPrice * DISCOUNT;
    } else {
        discount = originalPrice;
    }
    return discount;
}
}