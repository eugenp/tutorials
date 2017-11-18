package com.baeldung.jackson.general.jsonfilter;

import com.baeldung.jackson.domain.Person;
import com.baeldung.jackson.domain.Item;
import com.fasterxml.jackson.annotation.JsonFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
@JsonFilter("authorFilter")
public class Author extends Person {

    private List<Item> items = new ArrayList<>();

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
