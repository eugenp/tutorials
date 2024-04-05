package com.baeldung.webflux.exceptionhandeling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.webflux.exceptionhandeling.ex.NotFoundException;
import com.baeldung.webflux.exceptionhandeling.model.User;
import com.baeldung.webflux.exceptionhandeling.repository.UserRepository;

import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(MyRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{id}")
    public Mono<User> getUserByIdThrowingException(@PathVariable String id) {
        User user= userRepository.findById(id);
        if(user==null)
            throw new  NotFoundException("User Not Found");
        return  Mono.justOrEmpty(user);
    }

    @GetMapping("/user/{id}")
    public Mono<User> getUserByIdUsingMonoError(@PathVariable String id) {
        User user= userRepository.findById(id);
        if(user==null)
            return Mono.error(new NotFoundException("User Not Found"));
        return  Mono.justOrEmpty(user);
    }
}
