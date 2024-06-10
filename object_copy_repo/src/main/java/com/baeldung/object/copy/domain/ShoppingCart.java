package com.baeldung.object.copy.domain;

import java.util.List;

public class ShoppingCart implements Cloneable {

    private String cartName;
    private List<String> items;

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "ShoppingCart [cartName=" + cartName + ", items=" + items + "]";
    }
}
