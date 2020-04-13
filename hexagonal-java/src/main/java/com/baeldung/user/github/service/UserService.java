package com.baeldung.user.github.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.baeldung.user.github.model.User;

/**
 * Interface for user managing services
 */
public interface UserService {
	/**
	 * Abstract method to get the details of an user
	 */
	Optional<User> getUser(final UUID userId);
	
	/**
	 * Abstract method to get all the existing users
	 */
	List<User> getAllUsers();
	
	/**
	 * Abstract method to create a new user
	 */
	void createUser(User user);
	
	/**
	 * Abstract method to edit an user
	 */
	void editUser(UUID userId, User user);
	
	/**
	 * Abstract method to delete an user
	 */
	void deleteUser(UUID userId);
}
