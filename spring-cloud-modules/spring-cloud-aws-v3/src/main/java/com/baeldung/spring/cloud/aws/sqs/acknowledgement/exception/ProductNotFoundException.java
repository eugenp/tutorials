package com.baeldung.spring.cloud.aws.sqs.acknowledgement.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
