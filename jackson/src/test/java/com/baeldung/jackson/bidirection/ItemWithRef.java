package com.baeldung.jackson.bidirection;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class ItemWithRef {
    private int id;
    private String itemName;

    @JsonManagedReference
    private UserWithRef owner;

    public ItemWithRef() {
        super();
    }

    public ItemWithRef(final int id, final String itemName, final UserWithRef owner) {
        this.id = id;
        this.itemName = itemName;
        this.owner = owner;
    }
}
