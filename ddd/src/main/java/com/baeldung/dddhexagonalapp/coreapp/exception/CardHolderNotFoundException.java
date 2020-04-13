package com.baeldung.dddhexagonalapp.coreapp.exception;

public class CardHolderNotFoundException extends Exception {

    public CardHolderNotFoundException(String message) {
        super(message);
    }

    public CardHolderNotFoundException(Throwable cause) {
        super(cause);
    }

    public CardHolderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
