package com.baeldung.bootcustomfilters.controller;

import com.baeldung.bootcustomfilters.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Rest controller for User
 * @author hemant
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@GetMapping("")
	public List<User> getAllUsers() {
		LOG.info("Fetching all the users");
		return Arrays.asList(
				new User(UUID.randomUUID().toString(), "User1", "user1@test.com"),
				new User(UUID.randomUUID().toString(), "User2", "user2@test.com"),
				new User(UUID.randomUUID().toString(), "User3", "user3@test.com"));
	}

}
