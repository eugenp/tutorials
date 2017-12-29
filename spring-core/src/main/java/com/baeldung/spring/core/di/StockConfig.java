package com.baeldung.spring.core.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StockConfig {

    private int maxStocks;
    private int maxPrice;
    private int maxQuantity;

    // @Autowired not required if using a single constructor in class definition
    @Autowired
    public StockConfig(@Value("10") int maxStocks, @Value("10000") int maxPrice, @Value("5000") int maxQuantity) {
        this.maxStocks = maxStocks;
        this.maxPrice = maxPrice;
        this.maxQuantity = maxQuantity;
    }

    public int getMaxStocks() {
        return maxStocks;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void displayStockConfig() {
        System.out.println("Configuration by constructor arguments -- Max stocks: " + maxStocks + ", Max Price: " + maxPrice + ", Max Quantity: " + maxQuantity);
    }
}
