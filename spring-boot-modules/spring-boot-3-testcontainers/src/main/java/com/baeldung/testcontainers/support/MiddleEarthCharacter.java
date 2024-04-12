package com.baeldung.testcontainers.support;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("characters")
public record MiddleEarthCharacter(@Id String id, String name, String race) {
    public MiddleEarthCharacter(String name, String race) {
        this(UUID.randomUUID().toString(), name, race);
    }
}
