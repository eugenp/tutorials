package com.baeldung.ejb.spring.comparison.ejb.stateful;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;

@Stateful
public class ShoppingCartEJB implements ShoppingCartEJBRemote {
    private String name;
    private List<String> shoppingCart;

    public ShoppingCartEJB() {
        shoppingCart = new ArrayList<String>();
    }

    public void addItem(String item) {
        shoppingCart.add(item);
    }

    public List<String> getItems() {
        return shoppingCart;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
