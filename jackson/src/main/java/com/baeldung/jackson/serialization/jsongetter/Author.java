package com.baeldung.jackson.serialization.jsongetter;

import com.baeldung.jackson.domain.Item;
import com.baeldung.jackson.domain.Person;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.ArrayList;
import java.util.List;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class Author extends Person {

    List<Item> items = new ArrayList<>();

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @JsonGetter("publications")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
