package com.baeldung.adapter.inbound.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.core.domain.User;
import com.baeldung.port.inbound.UserService;

@RequestMapping("/user")
@RestController
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/add")
	public ResponseEntity<String> addNewUser(@RequestBody User user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.addNewUser(user));
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<User> getUser(@PathVariable String id) {
		return ResponseEntity.ok(userService.getUser(id));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> removeUser(@PathVariable String id) {
		return ResponseEntity.ok(userService.removeUser(id));
	}

	@GetMapping("/get")
	public ResponseEntity<Collection<User>> getAllUser() {
		return ResponseEntity.ok(userService.getAllUser());
	}
}
