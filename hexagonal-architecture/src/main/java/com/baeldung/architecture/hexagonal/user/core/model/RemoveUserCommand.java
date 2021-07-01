package com.baeldung.architecture.hexagonal.user.core.model;

public class RemoveUserCommand {

    private final String userId;

    public RemoveUserCommand(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
