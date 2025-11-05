package com.baeldung.lombok.defaultconstructorerror;

public class BaseEntityWithDefaultConstructor {

    private final String createdBy;

    protected BaseEntityWithDefaultConstructor() {
        this.createdBy = "system";
    }

    protected BaseEntityWithDefaultConstructor(String createdBy) {
        this.createdBy = createdBy;
    }
}
