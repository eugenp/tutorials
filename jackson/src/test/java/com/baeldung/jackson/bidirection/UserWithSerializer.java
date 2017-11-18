package com.baeldung.jackson.bidirection;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class UserWithSerializer {
    public int id;
    public String name;

    @JsonSerialize(using = CustomListSerializer.class)
    @JsonDeserialize(using = CustomListDeserializer.class)
    public List<ItemWithSerializer> userItems;

    public UserWithSerializer() {
        super();
    }

    public UserWithSerializer(final int id, final String name) {
        this.id = id;
        this.name = name;
        userItems = new ArrayList<ItemWithSerializer>();
    }

    public void addItem(final ItemWithSerializer item) {
        userItems.add(item);
    }
}
