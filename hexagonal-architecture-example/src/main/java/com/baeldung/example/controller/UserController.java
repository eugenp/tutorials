package com.baeldung.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.example.model.User;
import com.baeldung.example.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/users/{userId}")
	public User getUserByUserId(@PathVariable int userId) {
		User responseUser = userService.getUserByUserId(userId);
		return responseUser;
	}
	
	@PostMapping("/users")
	public User createNewUser(@RequestBody User user) {
		User responseUser = userService.createNewUser(user);
		return responseUser;
	}
	
}
