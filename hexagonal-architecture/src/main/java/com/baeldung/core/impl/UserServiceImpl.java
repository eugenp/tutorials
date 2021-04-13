package com.baeldung.core.impl;

import java.util.Collection;

import com.baeldung.core.domain.User;
import com.baeldung.port.inbound.UserService;
import com.baeldung.port.outbound.UserRepository;

public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public String addNewUser(User user) {
		return userRepository.addNewUser(user);
	}

	@Override
	public User getUser(String id) {
		return userRepository.getUser(id);
	}

	@Override
	public boolean removeUser(String id) {
		return userRepository.removeUser(id);
	}

	@Override
	public Collection<User> getAllUser() {
		return userRepository.getAllUser();
	}
}
