package com.baeldung.hexagonal;

public interface ShoppingCartService {

    Float getTotalCartValue();

    void addItem(String name, Integer quantity);
}
