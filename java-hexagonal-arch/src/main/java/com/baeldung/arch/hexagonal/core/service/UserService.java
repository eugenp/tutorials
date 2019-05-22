package com.baeldung.arch.hexagonal.core.service;

public interface UserService {
	public void addUser(User user) throws DuplicateUserIdException;

	public void updateUser(User user);

	public void addOrUpdateUser(User user);
}