package com.baeldung.java.hexagonal;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository class provides repository services
 * 
 */
public interface UserRepository {
        
        /**
         * Save the provided user into persistence store
         * @param user
         * @return
         */
	public User save(User user);
	
	/**
	 * Update the supplied user details
	 * 
	 * @param userId
	 * @return
	 * @throws UserNotFoundException
	 */
	public boolean update(int userId) throws UserNotFoundException;
	
	/**
	 * Returns all existing user details
	 * @return
	 */
	public Collection<User> findAll();
	
	/**
	 * Returns the user of supplied userid
	 * @param userId
	 * @return
	 */
	public Optional<User> findById(int userId);
}
