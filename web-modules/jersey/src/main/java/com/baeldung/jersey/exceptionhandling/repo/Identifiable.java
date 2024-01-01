package com.baeldung.jersey.exceptionhandling.repo;

import java.io.Serializable;

public interface Identifiable extends Serializable {
    void setId(String id);

    String getId();

    public static void assertValid(Identifiable i) {
        if (i == null)
            throw new IllegalArgumentException("object cannot be null");

        if (i.getId() == null)
            throw new IllegalArgumentException("object id cannot be null");
    }
}
