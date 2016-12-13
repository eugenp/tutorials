package com.baeldung.jacksonannotation.general.reference;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @JsonManagedReference
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
