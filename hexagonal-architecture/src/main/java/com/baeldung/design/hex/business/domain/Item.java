package com.baeldung.design.hex.business.domain;

public class Item {
    private String itemName;
    private int quantity;

    public Item(String itemName, int quantity) {
        super();
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
