package com.baeldung.lombok.defaultconstructorerror;

import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
public abstract class NoArgsBaseEntity {

    private final String createdBy;

    public NoArgsBaseEntity(String createdBy) {
        this.createdBy = createdBy;
    }
}