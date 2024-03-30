package com.baeldung.webflux.exceptionhandeling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.webflux.exceptionhandeling.ex.NotFoundException;
import com.baeldung.webflux.exceptionhandeling.repository.UserRepository;
import com.baeldung.webflux.zipwhen.model.User;

import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{id}")
    public Mono<User> getUserByIdThrowingException(@PathVariable String id) {
        return userRepository.findById(id)
          .switchIfEmpty(Mono.error(new NotFoundException("User not found")));
    }

    @GetMapping("/user/{id}")
    public Mono<User> getUserByIdUsingMonoError(@PathVariable String id) {
        return userRepository.findById(id)
          .switchIfEmpty(Mono.error(() -> new NotFoundException("User not found")));
    }
}
