package com.baeldung.jackson.bidirection;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserWithIgnore {
    private int id;
    private String name;

    @JsonIgnore
    private List<ItemWithIgnore> userItems;

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
