package com.baeldung.webclient.json.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
    private final String author;
    private final String title;

    @JsonCreator
    public Book(
            @JsonProperty("author") String author,
            @JsonProperty("title") String title) {
        this.author = author;
        this.title  = title;
    }
    public String getAuthor() {
        return this.author;
    }
}
