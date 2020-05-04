package com.baeldung.catalog.domain;

public class ObjectNotFoundException extends BusinessException {
    private static final String NOT_FOUND_MESSAGE = "Object specified by %s identifier could not be found";

    private String objectId;

    public ObjectNotFoundException(String objectId) {
        super(String.format(NOT_FOUND_MESSAGE, objectId));
        this.objectId = objectId;
    }

    public String getObjectId() {
        return this.objectId;
    }
}
