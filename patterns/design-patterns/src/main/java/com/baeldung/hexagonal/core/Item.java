package com.baeldung.hexagonal.core;

public class Item {
    String itemId;
    String itemName;
    String itemDescription;
    
    double price;
    
    public Item(String itemId, String itemName, String itemDescription, double price) {
        super();
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
    }
    
    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemDescription() {
        return itemDescription;
    }
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }        
}
