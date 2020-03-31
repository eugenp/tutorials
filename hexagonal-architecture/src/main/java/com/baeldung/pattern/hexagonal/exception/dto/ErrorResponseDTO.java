package com.baeldung.pattern.hexagonal.exception.dto;

import java.util.Date;

public class ErrorResponseDTO {

    private String errorCode;
    private Date timestamp;
    private String message;

    public ErrorResponseDTO(String errorCode, String message) {
        this.timestamp = new Date();
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorResponseDTO{" + "errorCode='" + errorCode + '\'' + ", timestamp=" + timestamp + ", message='" + message + '\'' + '}';
    }
}
