package com.baeldung.spring.reactive.customexception.controller;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.reactive.customexception.exception.CustomErrorException;
import com.baeldung.spring.reactive.customexception.exception.UserNotFoundException;
import com.baeldung.spring.reactive.customexception.model.CustomErrorResponse;
import com.baeldung.spring.reactive.customexception.model.ErrorDetails;
import com.baeldung.spring.reactive.customexception.model.User;

import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private Map<Long, User> userMap = new HashMap<>();

    @GetMapping("/v1/users/{userId}")
    public Mono<ResponseEntity<User>> getV1UserById(@PathVariable Long userId) {
        return Mono.fromCallable(() -> {
            User user = userMap.get(userId);
            if (user == null) {
                throw new UserNotFoundException("User not found with ID: " + userId);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        });
    }

    @GetMapping("/v2/users/{userId}")
    public Mono<ResponseEntity<User>> getV2UserById(@PathVariable Long userId) {
        return Mono.fromCallable(() -> {
            User user = userMap.get(userId);
            if (user == null) {
                CustomErrorResponse customErrorResponse = CustomErrorResponse
                  .builder()
                  .traceId(UUID.randomUUID().toString())
                  .timestamp(OffsetDateTime.now().now())
                  .status(HttpStatus.NOT_FOUND)
                  .errors(List.of(ErrorDetails.API_USER_NOT_FOUND))
                  .build();
                throw new CustomErrorException("User not found", customErrorResponse);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        });
    }

}
