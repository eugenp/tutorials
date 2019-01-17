package com.baeldung.hexagonal.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.baeldung.hexagonal.models.User;
import com.baeldung.hexagonal.repositories.UserRepositoryPort;
import com.baeldung.hexagonal.viewports.UserInterfacePort;

@Service
public class UserService {

	private final UserRepositoryPort userRepository;

	public UserService(UserRepositoryPort userRepository) {
		this.userRepository = userRepository;
	}

	public void addUser(User user, BindingResult bindingResult, UserInterfacePort userInterfacePort) {
		if (bindingResult.hasErrors()) {
			userInterfacePort.promptForCorrectData();
		} else {
			userRepository.addUser(user);
			userInterfacePort.goToProfile();
		}
	}
}
