//package com.baeldung.arch.hexagonal.adapter;

import com.baeldung.arch.hexagonal.core.UserService;

public class UserServiceImpl {
	public void addUser(User user) throws DuplicateUserIdException {
		UserService userService = new UserServiceImpl();
		userService.addUser(user);
	}

	public void updateUser(User user) {
		UserService userService = new UserServiceImpl();
		userService.updateUser(user);
	}

	public void addOrUpdateUser(User user) {
		UserService userService = new UserServiceImpl();
		userService.addOrUpdateUser(user);
	}
}