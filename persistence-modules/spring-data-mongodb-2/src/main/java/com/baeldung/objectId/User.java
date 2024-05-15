package com.baeldung.objectId;

import org.bson.types.ObjectId;

public class User {
    public static final String NAME_FIELD = "name";

    private final ObjectId id;
    private final String name;

    public User(ObjectId id, String name) {
        this.id = id;
        this.name = name;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

