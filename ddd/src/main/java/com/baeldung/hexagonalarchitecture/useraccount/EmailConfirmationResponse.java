package com.baeldung.hexagonalarchitecture.useraccount;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 14:35
 */
public class EmailConfirmationResponse {
    private final String message;

    private EmailConfirmationResponse(String message) {
        this.message = message;
    }

    public static EmailConfirmationResponse of(String message) {
        return new EmailConfirmationResponse(message);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "EmailConfirmationResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
