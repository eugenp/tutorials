package com.baeldung.hexagonal.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.port.UserRepoPort;
import com.baeldung.hexagonal.service.UserServicePort;

@Service
public class UserServiceAdapter implements UserServicePort {

	@Autowired
	UserRepoPort userRepoPort;

	@Override
	@Transactional
	public void createUser(String name) {

		User user = new User();
		user.setUserName(name);
		userRepoPort.createUser(user);

	}

	@Override
	public User getUser(Long userId) {
		return userRepoPort.getUser(userId);
	}
}
