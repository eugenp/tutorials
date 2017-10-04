package com.baeldung.jackson.serialization.jsonserialize;

import com.baeldung.jackson.domain.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class Item {

    private UUID id;
    private String title;
    private List<Person> authors = new ArrayList<>();
    private float price;

    public Item() {
    }

    public Item(String title, Author author) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.authors.add(author);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Person> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Person> authors) {
        this.authors = authors;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
