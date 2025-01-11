package com.baeldung.switchIfEmpty.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.switchIfEmpty.model.User;
import com.baeldung.switchIfEmpty.service.UserService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public Mono<ResponseEntity<User>> findUserDetails(@PathVariable("id") String id, @RequestParam("withDefer") boolean withDefer) {
        return (withDefer ? userService.findByUserIdWithDefer(id) : userService.findByUserIdWithoutDefer(id)).map(ResponseEntity::ok);
    }

}
