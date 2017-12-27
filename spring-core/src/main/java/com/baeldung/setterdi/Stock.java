package com.baeldung.setterdi;

import org.junit.Assert;

public class Stock {

    private String item;
    private int quantity;
    private float price;

    public void setItem(String item) {
        this.item = item;
        Assert.assertNotNull("Item not found!", item);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        Assert.assertTrue("Not a valid quantity!", quantity > 0);
    }

    public void setPrice(float price) {
        this.price = price;
        Assert.assertTrue("Not a valid price!", price > 0.0);
    }

    public void displayStock() {
        System.out.println("Stock details -- Item: " + item + ", Quantity: " + quantity + ", Price: " + price);
    }
}
