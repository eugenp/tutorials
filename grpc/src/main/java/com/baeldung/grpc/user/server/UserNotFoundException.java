package com.baeldung.grpc.user.server;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int id) {
        super(String.format("User not found with ID %s", id));
    }
}
