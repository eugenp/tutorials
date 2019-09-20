package com.baeldung.java.hexagonal;

import java.util.Collection;
import java.util.Optional;

/**
 *  Service class providing User CRUD services
 *
 */
public interface UserService {
	
        /**
         * Registers a new user
         * 
         * @param user
         * @return
         */
	public User registerUser(User user);
	
	/**
	 * Deactivates an existing user
	 * @param userId
	 * @return
	 */
	public boolean deactiveUser(int userId);
	
	/**
	 * Update an existing user details
	 * @param user
	 * @return
	 */
	public User updateUser(User user);
	
	/**
	 * Returns all users
	 * @return
	 */
	public Collection<User> getAllUsers();
	
	/**
	 * Returns the user of supplied user id
	 * @param userId
	 * @return
	 */
	public Optional<User> findUserById(int userId);

}
