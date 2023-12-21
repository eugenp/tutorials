package com.baeldung.entitydtodifferences.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookDto {

    @JsonProperty("NAME")
    private final String name;

    @JsonProperty("AUTHOR")
    private final String author;

    // Default constructor for Jackson deserialization
    public BookDto() {
        this(null, null);
    }

    public BookDto(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
}
