package com.baeldung;

import java.util.List;

public class ShoppingCart {
    long Id;
    List<Item> shoppingItems;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public List<Item> getShoppingItems() {
        return shoppingItems;
    }

    public void setShoppingItems(List<Item> shoppingItems) {
        this.shoppingItems = shoppingItems;
    }
}
