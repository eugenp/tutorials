package com.baeldung.junit5.testinstance;

public class TweetException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TweetException(String message) {
        super(message);
    }
}
