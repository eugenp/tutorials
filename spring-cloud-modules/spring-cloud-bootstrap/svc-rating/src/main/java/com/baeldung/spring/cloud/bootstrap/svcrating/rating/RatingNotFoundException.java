package com.baeldung.spring.cloud.bootstrap.svcrating.rating;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class RatingNotFoundException extends RuntimeException {
    RatingNotFoundException(String message) {
        super(message);
    }
}
