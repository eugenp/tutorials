package com.baeldung.joseanibalrodpez.hexagonalarchitecture.resource.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class RestExceptionHandler {

    private static final String ERROR_MESSAGE_CODE = "message";

    public static Map.Entry<String, String> of(ObjectError violation) {
        final String property = Stream.of(Objects.requireNonNull(violation.getCodes())).map(value -> {
            final String[] split = value.split("\\.");
            return String.join(".", Arrays.copyOfRange(split, 1, split.length));
        }).findFirst().orElse(violation.getObjectName());

        return new AbstractMap.SimpleEntry<>(property, violation.getDefaultMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handler(HttpMessageNotReadableException e) {
        final Map<String, String> response = Collections.singletonMap(ERROR_MESSAGE_CODE, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handler(MethodArgumentNotValidException e) {
        final Map<String, Object> response = new HashMap<>();
        final Map<String, Object> errors = e.getAllErrors().stream()
                .map(RestExceptionHandler::of)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        response.put(ERROR_MESSAGE_CODE, "There are some validation errors");
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}