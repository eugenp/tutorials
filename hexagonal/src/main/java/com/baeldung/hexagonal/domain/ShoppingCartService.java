package com.baeldung.hexagonal.domain;

public interface ShoppingCartService {

    Float getTotalCartValue();

    void addItem(String name, Integer quantity);
}
