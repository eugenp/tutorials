package com.baeldung.jackson.bidirection;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class ItemWithRef {
    public int id;
    public String itemName;

    @JsonBackReference
    public UserWithRef owner;

    public ItemWithRef() {
        super();
    }

    public ItemWithRef(final int id, final String itemName, final UserWithRef owner) {
        this.id = id;
        this.itemName = itemName;
        this.owner = owner;
    }
}
