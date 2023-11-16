package com.baeldung.entity_dto_differences.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserResponseDto {

    @JsonProperty("ID")
    private final Long id;

    @NotNull
    @JsonProperty("FIRST_NAME")
    private final String firstName;
    @NotNull
    @JsonProperty("LAST_NAME")
    private final String lastName;
    @NotNull
    @JsonProperty("BOOKS")
    private final List<BookDto> books;

    public UserResponseDto(Long id, String firstName, String lastName, List<BookDto> books) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<BookDto> getBooks() {
        return books;
    }
}
