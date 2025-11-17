package com.baeldung.lombok.defaultconstructorerror;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithTargeted extends BaseEntity {

    private String name;

    public UserWithTargeted(String createdBy, String name) {
        super(createdBy);
        this.name = name;
    }
}