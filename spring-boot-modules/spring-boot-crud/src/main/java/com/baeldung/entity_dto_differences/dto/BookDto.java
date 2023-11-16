package com.baeldung.entity_dto_differences.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class BookDto {

    @NotNull
    @JsonProperty("NAME")
    private final String name;

    @NotNull
    @JsonProperty("AUTHOR")
    private final String author;

    // Default constructor for Jackson deserialization
    public BookDto() {
        this.name = null;
        this.author = null;
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
