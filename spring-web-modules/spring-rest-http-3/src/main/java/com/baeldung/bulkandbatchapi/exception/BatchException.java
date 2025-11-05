package com.baeldung.bulkandbatchapi.exception;

public class BatchException extends RuntimeException {
    public BatchException(String exceptionMsg) {
        super(exceptionMsg);
    }
}
