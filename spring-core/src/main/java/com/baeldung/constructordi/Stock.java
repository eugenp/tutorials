package com.baeldung.constructordi;

import java.beans.ConstructorProperties;

import org.junit.Assert;

public class Stock {

    private final String item;
    private final int quantity;
    private final float price;

    @ConstructorProperties({ "item", "quantity", "price" })
    public Stock(String item, int quantity, float price) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        Assert.assertNotNull("Item not found!", item);
        Assert.assertTrue("Not a valid quantity!", quantity > 0);
        Assert.assertTrue("Not a valid price!", price > 0.0);
    }

    public void displayStock() {
        System.out.println("Stock details -- Item: " + item + ", Quantity: " + quantity + ", Price: " + price);
    }
}
