package com.baeldung.docker.dto;

public class VariableDto {

    private final String value;

    public VariableDto(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
