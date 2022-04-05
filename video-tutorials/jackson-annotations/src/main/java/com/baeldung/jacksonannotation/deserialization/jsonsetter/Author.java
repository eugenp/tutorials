package com.baeldung.jacksonannotation.deserialization.jsonsetter;

import com.baeldung.jacksonannotation.domain.Item;
import com.baeldung.jacksonannotation.domain.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
public class Author extends Person {

    private List<Item> items = new ArrayList<>();

    public Author(){
        super();
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
