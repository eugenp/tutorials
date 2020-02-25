package com.baeldung.hexagonalarchitecture.useraccount;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 13:20
 */
public class UserRegistrationResponse {
    private final String message;

    private UserRegistrationResponse(String message) {
        this.message = message;
    }

    public static UserRegistrationResponse of(String message) {
        return new UserRegistrationResponse(message);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "UserRegistrationResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
