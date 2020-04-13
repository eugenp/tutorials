package com.baeldung.user.github.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baeldung.user.github.dao.UserDAO;
import com.baeldung.user.github.exception.UserNotFoundException;
import com.baeldung.user.github.model.User;
import com.baeldung.user.github.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;

	/**
     * Method to pull the Specific User details
     */
	public Optional<User> getUser(final UUID userId) {
		Optional<User> user = userDAO.findById(userId);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User with id "+userId+" doesn't exist");
		}
		return user;
	}

	/**
     * Method to pull the all the user details
     */
	public List<User> getAllUsers() {
		return (List<User>) userDAO.findAll();
	}

	/**
     * Method to create a new user
     */
	public void createUser(User user) {
		userDAO.save(user);
	}

	/**
     * Method to edit the specific the user details
     */
	public void editUser(UUID userId, User user) {
		Optional<User> existingUser = userDAO.findById(userId);
		if(!existingUser.isPresent()) {
			throw new UserNotFoundException("User with id "+userId+" doesn't exist");
		}
		userDAO.save(user);
	}
	
	/**
     * Method to delete the user by passing userid
     */
	public void deleteUser(UUID userId) {
		Optional<User> existingUser = userDAO.findById(userId);
		if(!existingUser.isPresent()) {
			throw new UserNotFoundException("User with id "+userId+" doesn't exist");
		}
		userDAO.deleteById(userId);
	}
}
