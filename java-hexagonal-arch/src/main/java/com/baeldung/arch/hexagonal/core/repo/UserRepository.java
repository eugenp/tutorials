package com.baeldung.arch.hexagonal.core.repo;

public class UserRepository {
	public void insertUser(User user) throws DuplicateUserIdException {
		// Inserts an user in DB, if user id exists throws DuplicateUserIdException
	}

	public void updateUser(User user) {
		// Updates an user
	}

	public void upsertUser(User user) {
		// Inserts or updates an user
		try {
			insertUser(user);
		} catch (DuplicateUserIdException e) {
			updateUser(user);
		}
	}
}