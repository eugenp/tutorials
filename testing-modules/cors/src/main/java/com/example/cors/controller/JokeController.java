package com.example.cors.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.datafaker.Faker;

@RestController
@RequestMapping("/api/v1")
public class JokeController {

    private static final Faker FAKER = new Faker();

    @GetMapping(value = "/joke")
    public ResponseEntity<JokeResponse> generate() {
        final var joke = FAKER.joke().pun();
        final var remainingLimit = FAKER.number().digit();

        return ResponseEntity.ok()
          .header("X-Rate-Limit-Remaining", remainingLimit)
          .body(new JokeResponse(joke));
    }

    record JokeResponse(String joke) {};

}