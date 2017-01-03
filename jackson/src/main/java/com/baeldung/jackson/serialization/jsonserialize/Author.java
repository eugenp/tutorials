package com.baeldung.jackson.serialization.jsonserialize;

import com.baeldung.jackson.domain.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class Author extends Person {

    List<com.baeldung.jackson.domain.Item> items = new ArrayList<>();

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public List<com.baeldung.jackson.domain.Item> getItems() {
        return items;
    }

    public void setItems(List<com.baeldung.jackson.domain.Item> items) {
        this.items = items;
    }
}
