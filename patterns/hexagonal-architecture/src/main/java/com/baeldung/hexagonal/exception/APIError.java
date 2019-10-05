package com.baeldung.hexagonal.exception;

public class APIError {
    private String code;

    private String details;

    public APIError() {
    }

    public APIError(String code, String details) {
        this.code = code;
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "APIError{" + "code='" + code + '\'' + ", details='" + details + '\'' + '}';
    }
}