package com.baeldung.okhttp;

public class DecodeException extends Exception {
    public DecodeException(String message) {
        super(message);
    }

    public DecodeException(Throwable cause) {
        super(cause);
    }
}
