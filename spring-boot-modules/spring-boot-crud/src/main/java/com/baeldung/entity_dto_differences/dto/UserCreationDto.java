package com.baeldung.entity_dto_differences.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserCreationDto {

    @NotNull
    @JsonProperty("FIRST_NAME")
    private final String firstName;

    @NotNull
    @JsonProperty("LAST_NAME")
    private final String lastName;

    @NotNull
    @JsonProperty("ADDRESS")
    private final String address;

    @NotNull
    @JsonProperty("BOOKS")
    private final List<BookDto> books;

    // Default constructor for Jackson deserialization
    public UserCreationDto() {
        this.firstName = null;
        this.lastName = null;
        this.address = null;
        this.books = null;
    }

    public UserCreationDto(String firstName, String lastName, String address, List<BookDto> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.books = books;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() { return address; }

    public List<BookDto> getBooks() {
        return books;
    }
}
