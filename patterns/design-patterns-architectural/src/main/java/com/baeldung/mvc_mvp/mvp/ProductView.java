package com.baeldung.mvc_mvp.mvp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductView {
    private static Logger log = LoggerFactory.getLogger(ProductView.class);
    
    public void printProductDetails(String name, String description, Double price) {
        log.info("Product details:");
        log.info("product Name: " + name);
        log.info("product Description: " + description);
        log.info("product price: " + price);
    }
}
