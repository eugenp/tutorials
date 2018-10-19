package com.baeldung.jackson.deserialization.jsondeserialize;

import com.baeldung.jackson.domain.Item;
import com.baeldung.jackson.domain.Person;

import java.util.ArrayList;
import java.util.List;

public class Author extends Person {

    List<Item> items = new ArrayList<>();

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
