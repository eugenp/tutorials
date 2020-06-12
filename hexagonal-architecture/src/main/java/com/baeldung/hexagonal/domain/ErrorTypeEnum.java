package com.baeldung.hexagonal.domain;

public enum ErrorTypeEnum {
    URL_NOT_FOUND("Url not found for given code"),
    EMPTY_URL("Empty Url"),
    MALFORMED_URL("Malformed Url");

    private String value;

    ErrorTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
