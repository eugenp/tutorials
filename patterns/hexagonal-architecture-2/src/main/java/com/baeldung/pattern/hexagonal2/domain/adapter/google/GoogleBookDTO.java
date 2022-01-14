package com.baeldung.pattern.hexagonal2.domain.adapter.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookDTO implements Serializable {

    private List<ItemsDTO> items;

    public GoogleBookDTO() {
    }

    public List<ItemsDTO> getItems() {
        return items;
    }

    public void setItem(List<ItemsDTO> items) {
        this.items = items;
    }
}