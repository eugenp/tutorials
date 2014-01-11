package org.baeldung.jackson.dtos;

import org.baeldung.jackson.serialization.ItemSerializerOnClass;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ItemSerializerOnClass.class)
public class ItemWithSerializer {
    public final int id;
    public final String itemNr;
    public final User owner;

    public ItemWithSerializer(final int id, final String itemNr, final User owner) {
        this.id = id;
        this.itemNr = itemNr;
        this.owner = owner;
    }

    // API

    public int getId() {
        return id;
    }

    public String getItemNr() {
        return itemNr;
    }

    public User getOwner() {
        return owner;
    }

}