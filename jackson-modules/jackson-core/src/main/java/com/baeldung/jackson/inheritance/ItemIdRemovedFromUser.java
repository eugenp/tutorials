package com.baeldung.jackson.inheritance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("itemIdRemovedFromUser")
public class ItemIdRemovedFromUser extends Event {
    private final String itemId;
    private final Long quantity;

    @JsonCreator
    public ItemIdRemovedFromUser(@JsonProperty("id") String id, @JsonProperty("timestamp") Long timestamp, @JsonProperty("itemId") String itemId, @JsonProperty("quantity") Long quantity) {
        super(id, timestamp);
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public String getItemId() {
        return itemId;
    }

    public Long getQuantity() {
        return quantity;
    }
}