package com.baeldung.jackson.annotation.bidirection;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserWithIgnore {
    public int id;
    public String name;

    @JsonIgnore
    public List<ItemWithIgnore> userItems;

    public UserWithIgnore() {
        super();
    }

    public UserWithIgnore(final int id, final String name) {
        this.id = id;
        this.name = name;
        userItems = new ArrayList<ItemWithIgnore>();
    }

    public void addItem(final ItemWithIgnore item) {
        userItems.add(item);
    }
}
