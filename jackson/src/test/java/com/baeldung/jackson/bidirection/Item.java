package com.baeldung.jackson.bidirection;

public class Item {
    private int id;
    private String itemName;
    private User owner;

    public Item() {
        super();
    }

    public Item(final int id, final String itemName, final User owner) {
        this.id = id;
        this.itemName = itemName;
        this.owner = owner;
    }
}
