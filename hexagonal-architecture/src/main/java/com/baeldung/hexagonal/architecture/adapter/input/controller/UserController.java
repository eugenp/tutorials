package com.baeldung.hexagonal.architecture.adapter.input.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.architecture.core.domain.User;
import com.baeldung.hexagonal.architecture.core.service.UserServiceImpl;

@RestController
@RequestMapping("/api")
public class UserController implements UserRestAdapter {

	@Autowired
	UserServiceImpl userService;

	@Override
	public Optional<User> findUserById(@PathVariable(name = "id") Long id) {
		return userService.findUserById(id);
	}

	@Override
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
}
