package com.baeldung.jackson.bidirection;

import java.util.ArrayList;
import java.util.List;

public class User {
    public int id;
    public String name;
    public List<Item> userItems;

    public User() {
        super();
    }

    public User(final int id, final String name) {
        this.id = id;
        this.name = name;
        userItems = new ArrayList<Item>();
    }

    public void addItem(final Item item) {
        userItems.add(item);
    }
}
