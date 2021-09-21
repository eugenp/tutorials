package com.baeldung.hexagonal.api.controller;

import com.baeldung.hexagonal.domain.model.User;
import com.baeldung.hexagonal.domain.port.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
  // TODO log requests
  private final UserService userService;

  @PostMapping
  public User addUser(@RequestBody User user) {
    return userService.addUser(user);
  }

  @GetMapping("{id}")
  public User getUser(@PathVariable long id) {
    // TODO add ControllerAdvice?
    return userService.getUserById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));
  }
}
