package com.baeldung.hexagonal.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CreatePersonDto {

    private final String name;

    @JsonCreator
    public CreatePersonDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
