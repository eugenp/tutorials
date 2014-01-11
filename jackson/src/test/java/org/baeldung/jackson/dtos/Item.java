package org.baeldung.jackson.dtos;

public class Item {
    public final int id;
    public final String itemNr;
    public final User createdBy;

    public Item(final int id, final String itemNr, final User createdBy) {
        this.id = id;
        this.itemNr = itemNr;
        this.createdBy = createdBy;
    }

    // API

    public int getId() {
        return id;
    }

    public String getItemNr() {
        return itemNr;
    }

    public User getCreatedBy() {
        return createdBy;
    }

}