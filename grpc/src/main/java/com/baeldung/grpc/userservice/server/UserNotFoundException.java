package com.baeldung.grpc.userservice.server;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int userId) {
        super(String.format("User not found with ID %s", userId));
    }
}
