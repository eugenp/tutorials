package com.baeldung.jackson.bidirection;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class Item {

    public int id;
    public String itemName;
    public User owner;

    @JsonBackReference(value="soldItemsRef")
    public User soldOwner;
    @JsonBackReference(value="wishlistRef")
    public User wishlistOwner;

    public Item() {
        super();
    }

    public Item(final int id, final String itemName, final User owner) {
        this.id = id;
        this.itemName = itemName;
        this.owner = owner;
    }
}
