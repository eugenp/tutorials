package com.baeldung.jackson.bidirection;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.jackson.jsonview.Views;

import com.fasterxml.jackson.annotation.JsonView;

public class UserWithView {
    @JsonView(Views.Public.class)
    private int id;

    @JsonView(Views.Public.class)
    private String name;

    @JsonView(Views.Internal.class)
    private List<ItemWithView> userItems;

    public UserWithView() {
        super();
    }

    public UserWithView(final int id, final String name) {
        this.id = id;
        this.name = name;
        userItems = new ArrayList<>();
    }

    public void addItem(final ItemWithView item) {
        userItems.add(item);
    }
}
