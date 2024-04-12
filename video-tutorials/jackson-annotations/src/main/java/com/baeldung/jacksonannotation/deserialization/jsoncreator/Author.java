package com.baeldung.jacksonannotation.deserialization.jsoncreator;


import com.baeldung.jacksonannotation.domain.Item;
import com.baeldung.jacksonannotation.domain.Person;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 * @see JsonGetter
 */
public class Author extends Person {

    List<Item> items = new ArrayList<>();

    @JsonCreator
    public Author(
            @JsonProperty("christianName") String firstName,
            @JsonProperty("surname") String lastName) {
        super(firstName, lastName);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
