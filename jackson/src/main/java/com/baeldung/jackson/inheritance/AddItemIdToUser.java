package com.baeldung.jackson.inheritance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("addItemIdToUserEvent")
public class AddItemIdToUser extends Event {
    private final String itemId;
    private final Long quantity;

    @JsonCreator
    public AddItemIdToUser(@JsonProperty("metadata") Metadata metadata,
                           @JsonProperty("itemId") String itemId,
                           @JsonProperty("quantity") Long quantity) {
        super(metadata);
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