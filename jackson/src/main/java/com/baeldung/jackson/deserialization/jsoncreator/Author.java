package com.baeldung.jackson.deserialization.jsoncreator;

import com.baeldung.jackson.domain.Person;
import com.baeldung.jackson.domain.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Author extends Person {

    List<Item> items = new ArrayList<>();

    @JsonCreator
    public Author(@JsonProperty("christianName") String firstName, @JsonProperty("surname") String lastName) {
        super(firstName, lastName);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
