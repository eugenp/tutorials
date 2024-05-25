package com.baeldung.bulkandbatchapi.exception;

public class BatchCreateException extends RuntimeException {
    public BatchCreateException(String exceptionMsg) {
        super(exceptionMsg);
    }
}
