package com.baeldung.pattern.hexagonal2.domain.adapter.openlib;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibBookDTO implements Serializable {
    private String title;

    public OpenLibBookDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}