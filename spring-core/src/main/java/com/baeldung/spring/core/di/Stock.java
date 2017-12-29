package com.baeldung.spring.core.di;

public class Stock {

    private final int id;
    private final String item;
    private final int quantity;
    private final float price;

    public Stock(int id, String item, int quantity, float price) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public void displayStock() {
        System.out.println("Stock details -- Id " + id + ", Item: " + item + ", Quantity: " + quantity + ", Price: " + price);
    }
}
