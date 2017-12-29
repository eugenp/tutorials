package com.baeldung.spring.core.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StockConfigBySetter {

    private int maxStocks;
    private int maxPrice;
    private int maxQuantity;

    @Autowired
    public void setMaxStocks(@Value("10") int maxStocks) {
        this.maxStocks = maxStocks;
    }

    @Autowired
    public void setMaxPrice(@Value("10000") int maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Autowired
    public void setMaxQuantity(@Value("5000") int maxQuantity) {
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
        System.out.println("Configuration by using setter injection -- Max stocks: " + maxStocks + ", Max Price: " + maxPrice + ", Max Quantity: " + maxQuantity);
    }
}