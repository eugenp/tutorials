package com.baeldung.hexagonalarchitecture.userside.response;

import java.util.UUID;

public class UserCreateResponse {
    private UUID id;

    public UserCreateResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
