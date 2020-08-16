package com.baeldung.model;

import com.baeldung.controller.View;
import com.fasterxml.jackson.annotation.JsonView;

public class Item {

    @JsonView(View.User.class)
    private int id;
    @JsonView(View.User.class)
    private String name;
    @JsonView(View.Admin.class)
    private String ownerName;

    public Item(int id, String name, String ownerName) {
        this.id = id;
        this.name = name;
        this.ownerName = ownerName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
