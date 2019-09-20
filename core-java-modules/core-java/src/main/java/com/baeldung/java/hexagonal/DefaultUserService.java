package com.baeldung.java.hexagonal;

import java.util.Collection;
import java.util.Optional;

/**
 *  Default implementation of user service 
 */
public class DefaultUserService implements UserService{

	private UserRepository userRepository;
	
	@Override
	public User registerUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public boolean deactiveUser(int userId) throws UserNotFoundException {
		return userRepository.update(userId);
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public Collection<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findUserById(int userId) {
		return userRepository.findById(userId);
	}

}
