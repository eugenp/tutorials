package com.baeldung.jacksonannotation.deserialization.jacksoninject;

import com.baeldung.jacksonannotation.domain.Item;

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

    public Author(){}

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
