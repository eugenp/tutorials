package com.example.cors.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.datafaker.Faker;

@RestController
@RequestMapping("/api/v1")
public class JokeController {

    @GetMapping(value = "/joke")
    public ResponseEntity<JokeResponse> generate() {
        final var joke = new Faker().joke().pun();
        return ResponseEntity.ok(new JokeResponse(joke));
    }

    record JokeResponse(String joke) {};

}