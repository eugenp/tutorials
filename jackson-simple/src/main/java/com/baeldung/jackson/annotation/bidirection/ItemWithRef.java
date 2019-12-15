package com.baeldung.jackson.annotation.bidirection;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class ItemWithRef {
    public int id;
    public String itemName;

    @JsonManagedReference
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
