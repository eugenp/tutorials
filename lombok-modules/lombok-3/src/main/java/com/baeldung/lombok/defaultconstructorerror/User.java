package com.baeldung.lombok.defaultconstructorerror;

import lombok.Data;

@Data
public class User extends BaseEntity {

    private String name;

    public User(String createdBy, String name) {
        super(createdBy);
        this.name = name;
    }
}