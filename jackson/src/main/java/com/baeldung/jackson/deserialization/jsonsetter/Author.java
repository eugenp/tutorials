package com.baeldung.jackson.deserialization.jsonsetter;

import com.baeldung.jackson.domain.Item;
import com.baeldung.jackson.domain.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class Author extends Person {

    private List<Item> items = new ArrayList<>();

    public Author() {
    }

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @JsonIgnore
    public List<Item> getItems() {
        return items;
    }

    @JsonSetter("publications")
    public void setItems(List<Item> items) {
        this.items = items;
    }
}
