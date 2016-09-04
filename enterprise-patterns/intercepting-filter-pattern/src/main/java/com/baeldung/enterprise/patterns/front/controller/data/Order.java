package com.baeldung.enterprise.patterns.front.controller.data;

import java.util.Map;

public interface Order {
    String getUsername();

    Map<Book, Integer> getItems();

    void add(Book item, Integer quantity);
}
