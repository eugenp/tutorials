package com.baeldung.ambassadorpattern;

import org.springframework.web.client.HttpClientErrorException;

public class ExternalApiException extends RuntimeException {

    public ExternalApiException(String message, HttpClientErrorException stackTrace) {
        super(message, stackTrace);
    }
}
