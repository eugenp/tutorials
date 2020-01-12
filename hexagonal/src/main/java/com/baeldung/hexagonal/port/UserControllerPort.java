package com.baeldung.hexagonal.port;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.hexagonal.domain.User;

public interface UserControllerPort {

	@PostMapping("/createUser")
	public void createUser(@RequestBody User request);

	@GetMapping("/getUser/{userId}")
	public User getUser(@PathVariable Long userId);
}
