package com.baeldung.jackson.bidirection;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.jackson.bidirection.jsonview.Views;

import com.fasterxml.jackson.annotation.JsonView;

public class UserWithView {
    @JsonView(Views.Public.class)
    public int id;

    @JsonView(Views.Public.class)
    public String name;

    @JsonView(Views.Internal.class)
    public List<ItemWithView> userItems;

    public UserWithView() {
        super();
    }

    public UserWithView(final int id, final String name) {
        this.id = id;
        this.name = name;
        userItems = new ArrayList<ItemWithView>();
    }

    public void addItem(final ItemWithView item) {
        userItems.add(item);
    }
}
