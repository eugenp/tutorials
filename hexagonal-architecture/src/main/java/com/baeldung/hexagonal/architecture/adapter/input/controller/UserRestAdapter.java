package com.baeldung.hexagonal.architecture.adapter.input.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.hexagonal.architecture.core.domain.User;

public interface UserRestAdapter {

	@GetMapping("/users/{id}")
	User createUser(@RequestBody User user);

	@PostMapping("/users")
	Optional<User> findUserById(@PathVariable(name = "id") Long id);

}
