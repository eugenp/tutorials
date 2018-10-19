package com.baeldung.patterns.intercepting.filter.data;

import java.util.HashMap;
import java.util.Map;

public class OrderImpl implements Order {
    private String username;
    private Map<Book, Integer> items = new HashMap<>();

    public OrderImpl(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Map<Book, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Book, Integer> items) {
        this.items = items;
    }

    @Override
    public void add(Book item, Integer quantity) {
        Integer q = 0;
        if (this.items.containsKey(item)) {
            q = this.items.get(item);
        }
        this.items.put(item, quantity + q);
    }
}
