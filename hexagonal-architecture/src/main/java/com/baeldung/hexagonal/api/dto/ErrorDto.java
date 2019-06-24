package com.baeldung.hexagonal.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Value
public final class ErrorDto {

    HttpStatus status;
    String reason;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    LocalDateTime timestamp;

    public ErrorDto(HttpStatus status, String reason) {
        this.status = status;
        this.reason = reason;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorDto(HttpStatus status) {
        this.status = status;
        this.reason = "Unknown error";
        this.timestamp = LocalDateTime.now();
    }
}
