package com.baeldung.hexagonal.exception;

import org.springframework.http.HttpStatus;

public class ApiRequestException extends RuntimeException {

    /**
     * Serial id for serialisation
     */
    private static final long serialVersionUID = 5161276820534865074L;
    private APIError apiError;
    private HttpStatus status;

    public ApiRequestException(HttpStatus status, APIError apiError) {
        super(apiError.toString());
        this.status = status;
        this.apiError = apiError;
    }

    public APIError getApiError() {
        return apiError;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
