package com.baeldung.user.github.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.baeldung.user.github.model.User;
import com.baeldung.user.github.service.UserService;

/**
 * This is the RestController to manage users
 */
@RestController
public class UserController {
	
	/**
	 * Injecting UserService bean to handle CRUD Operations for users
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * This method invokes the UserService implementation to fetch all the users from the database
	 */
	@RequestMapping("/users")
	public List<User> listAllUsers(){
		return userService.getAllUsers();
	}
	
	/**
	 * This method invokes the UserService implementation to fetch the details of a specific user
	 */
	@RequestMapping("/users/{userId}")
	public Optional<User> getUser(@PathVariable("userId") UUID userId) {
		return userService.getUser(userId);
	}
	
	/**
	 * This method invokes the UserService implementation to create a new User
	 */
	@RequestMapping(method = RequestMethod.POST, value="/users")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createUser(@Valid @RequestBody User user) {
		userService.createUser(user);
	}
	
	/**
	 * This method invokes the UserService implementation to edit the existing users
	 */
	@RequestMapping(method = RequestMethod.PUT, value="/users/{userId}")
	public void editUser(@PathVariable UUID userId, @Valid @RequestBody User user) {
		userService.editUser(userId, user);
	}
	
	/**
	 * This method invokes the UserService implementation to delete the existing user
	 */
	@RequestMapping(method = RequestMethod.DELETE, value="/users/{userId}")
	public void deleteUser(@PathVariable UUID userId) {
		userService.deleteUser(userId);
	}
	
}

