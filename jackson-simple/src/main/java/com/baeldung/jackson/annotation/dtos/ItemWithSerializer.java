package com.baeldung.jackson.annotation.dtos;

import com.baeldung.jackson.annotation.deserialization.ItemDeserializerOnClass;
import com.baeldung.jackson.annotation.serialization.ItemSerializerOnClass;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ItemSerializerOnClass.class)
@JsonDeserialize(using = ItemDeserializerOnClass.class)
public class ItemWithSerializer {
    public final int id;
    public final String itemName;
    public final User owner;

    public ItemWithSerializer(final int id, final String itemName, final User owner) {
        this.id = id;
        this.itemName = itemName;
        this.owner = owner;
    }

    // API

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public User getOwner() {
        return owner;
    }

}