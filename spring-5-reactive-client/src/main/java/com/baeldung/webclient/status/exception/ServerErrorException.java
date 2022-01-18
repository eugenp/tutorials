package com.baeldung.webclient.status.exception;

public class ServerErrorException extends Exception {
    public ServerErrorException(String message) {
        super(message);
    }
}
