package com.baeldung.bulkandbatchapi;

public class BatchCreateException extends RuntimeException {
    public BatchCreateException(String exceptionMsg) {
        super(exceptionMsg);
    }
}
