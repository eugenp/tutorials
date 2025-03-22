package com.baeldung.jdbc.mocking;

public record Customer(int id, String name, Status status) {

    public enum Status {
        ACTIVE, LOYAL, INACTIVE
    }
}