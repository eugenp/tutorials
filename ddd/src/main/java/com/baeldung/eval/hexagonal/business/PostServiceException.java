package com.baeldung.eval.hexagonal.business;

public class PostServiceException extends Exception {
    public PostServiceException(Exception ex) {
        super(ex);
    }

    public PostServiceException(String message) {
        super(message);
    }
}
