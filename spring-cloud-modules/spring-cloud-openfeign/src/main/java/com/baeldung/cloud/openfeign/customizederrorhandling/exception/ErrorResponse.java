package com.baeldung.cloud.openfeign.customizederrorhandling.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    @JsonProperty(value = "code")
    private int code;

    @JsonProperty(value = "status")
    private String status;
    @JsonProperty(value = "message")
    private String message;
    @JsonProperty(value = "details")
    private String details;

    public ErrorResponse() {
    }

    public ErrorResponse(HttpStatus httpStatus, String message, String details) {
        timestamp = new Date();
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
