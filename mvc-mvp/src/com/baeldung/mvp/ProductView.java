package com.baeldung.mvp;

public class ProductView {
    public void printProductDetails(String productName, String productDescription, Double productPrice) {
        System.out.println("Product details:");
        System.out.println("product Name: " + productName);
        System.out.println("product Description: " + productDescription);
        System.out.println("product price: " + productPrice);
        
    }
}
