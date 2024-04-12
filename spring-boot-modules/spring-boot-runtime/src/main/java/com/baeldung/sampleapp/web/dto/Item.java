package com.baeldung.sampleapp.web.dto;

import com.fasterxml.jackson.annotation.JsonView;

public class Item {
    @JsonView(Views.Public.class)
    public int id;

    @JsonView(Views.Public.class)
    public String itemName;

    @JsonView(Views.Internal.class)
    public String ownerName;

    public Item() {
        super();
    }

    public Item(final int id, final String itemName, final String ownerName) {
        this.id = id;
        this.itemName = itemName;
        this.ownerName = ownerName;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getOwnerName() {
        return ownerName;
    }
}