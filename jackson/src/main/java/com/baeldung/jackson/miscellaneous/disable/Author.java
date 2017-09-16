package com.baeldung.jackson.miscellaneous.disable;

import com.baeldung.jackson.domain.Item;
import com.baeldung.jackson.domain.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "lastName", "items", "firstName", "id" })
public class Author extends Person {

    @JsonIgnore
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
