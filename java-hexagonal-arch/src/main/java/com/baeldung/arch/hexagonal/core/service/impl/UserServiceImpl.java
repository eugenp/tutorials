
//package com.baeldung.arch.hexagonal.core.service.impl;

import com.baeldung.arch.hexagonal.core.repo.UserRepository;

public class UserServiceImpl {
	public void addUser(User user) throws DuplicateUserIdException {
		new UserRepository().insertUser(user);
	}

	public void updateUser(User user) {
		new UserRepository().updateUser(user);
	}

	public void addOrUpdateUser(User user) {
		new UserRepository().upsertUser(user);
	}
}