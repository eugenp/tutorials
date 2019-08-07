package com.baeldung.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.example.model.User;
import com.baeldung.example.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUserByUserId(int userId) {
		User userResponse = userRepository.getUserByUserId(userId);
		return userResponse;
	}

	@Override
	public User createNewUser(User user) {
		User userResponse = userRepository.createNewUser(user);
		return userResponse;
	}

}
