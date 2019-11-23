package com.baeldung.jackson.bidirection;

import com.baeldung.jackson.bidirection.jsonview.Views;

import com.fasterxml.jackson.annotation.JsonView;

public class ItemWithView {
    @JsonView(Views.Public.class)
    public int id;

    @JsonView(Views.Public.class)
    public String itemName;

    @JsonView(Views.Public.class)
    public UserWithView owner;

    public ItemWithView() {
        super();
    }

    public ItemWithView(final int id, final String itemName, final UserWithView owner) {
        this.id = id;
        this.itemName = itemName;
        this.owner = owner;
    }
}
