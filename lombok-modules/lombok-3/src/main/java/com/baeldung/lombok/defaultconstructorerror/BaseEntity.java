package com.baeldung.lombok.defaultconstructorerror;

public class BaseEntity {

    private final String createdBy;

    protected BaseEntity(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }
}
