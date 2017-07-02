package com.baeldung.jackson.bidirection;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class UserWithRef {
    private int id;
    private String name;

    @JsonBackReference
    private List<ItemWithRef> userItems;

    public UserWithRef() {
        super();
    }

    public UserWithRef(final int id, final String name) {
        this.id = id;
        this.name = name;
        userItems = new ArrayList<ItemWithRef>();
    }

    public void addItem(final ItemWithRef item) {
        userItems.add(item);
    }
}
