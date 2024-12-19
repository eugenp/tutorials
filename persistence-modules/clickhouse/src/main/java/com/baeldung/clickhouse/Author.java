package com.baeldung.clickhouse;

import java.time.LocalDateTime;
import java.util.UUID;

public record Author(
    UUID id,
    String name,
    String email,
    LocalDateTime createdAt) {

    public static Author create(String name, String email) {
        return new Author(
            UUID.randomUUID(),
            name,
            email,
            LocalDateTime.now()
        );
    }

}