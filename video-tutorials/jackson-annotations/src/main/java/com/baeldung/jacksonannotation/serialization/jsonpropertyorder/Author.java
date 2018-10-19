package com.baeldung.jacksonannotation.serialization.jsonpropertyorder;


import com.baeldung.jacksonannotation.domain.Item;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
@JsonPropertyOrder(value = {"items", "firstName", "lastName", "id"}, alphabetic = true)
public class Author extends Person {

    private String zIndex;

    private String alphaIndex;

    List<Item> items = new ArrayList<>();

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getzIndex() {
        return zIndex;
    }

    public void setzIndex(String zIndex) {
        this.zIndex = zIndex;
    }

    public String getAlphaIndex() {
        return alphaIndex;
    }

    public void setAlphaIndex(String alphaIndex) {
        this.alphaIndex = alphaIndex;
    }
}
