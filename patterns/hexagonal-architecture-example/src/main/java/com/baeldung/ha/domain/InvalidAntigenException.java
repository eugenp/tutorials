package com.baeldung.ha.domain;

public class InvalidAntigenException extends Exception {
    private Integer value;

    public InvalidAntigenException(Integer antigenValue) {
        this.value = antigenValue;
    }

    public Integer getValue() {
        return value;
    }
}
