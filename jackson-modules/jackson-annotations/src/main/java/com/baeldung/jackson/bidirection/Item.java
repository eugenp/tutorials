package com.baeldung.jackson.bidirection;

public class Item {
    public int id;
    public String itemName;
    public User owner;

    public Item() {
        super();
    }

    public Item(final int id, final String itemName, final User owner) {
        this.id = id;
        this.itemName = itemName;
        this.owner = owner;
    }
}
