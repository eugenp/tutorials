package com.baeldung.entitydtodifferences.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCreationDto {

    @JsonProperty("FIRST_NAME")
    private final String firstName;

    @JsonProperty("LAST_NAME")
    private final String lastName;

    @JsonProperty("ADDRESS")
    private final String address;

    @JsonProperty("BOOKS")
    private final List<BookDto> books;

    // Default constructor for Jackson deserialization
    public UserCreationDto() {
        this(null, null, null, null);
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

    public String getAddress() {
        return address;
    }

    public List<BookDto> getBooks() {
        return books;
    }
}
