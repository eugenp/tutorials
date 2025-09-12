package com.baeldung.jackson.bidirection;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class User {

    public int id;
    public String name;
    public List<Item> userItems;

    @JsonManagedReference(value="wishlistRef")
    public List<Item> wishlist = new ArrayList<>();
    @JsonManagedReference(value="soldItemsRef")
    public List<Item> soldItems = new ArrayList<>();

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
