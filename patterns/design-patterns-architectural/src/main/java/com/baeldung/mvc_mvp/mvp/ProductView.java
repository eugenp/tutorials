package com.baeldung.mvc_mvp.mvp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductView {
    private static Logger log = LoggerFactory.getLogger(ProductView.class);
    
    public void printProductDetails(String productName, String productDescription, Double productPrice) {
        log.info("Product details:");
        log.info("product Name: " + productName);
        log.info("product Description: " + productDescription);
        log.info("product price: " + productPrice);
        
    }
}
