package com.baeldung.hexagon.domain.services;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagon.domain.model.User;
import com.baeldung.hexagon.domain.ports.IRegistrationService;
import com.baeldung.hexagon.domain.ports.IUserAccess;

@Service
public class RegistrationService implements IRegistrationService {

	@Autowired
	IUserAccess userAccess;

	@Override
	public void registerUser(User user) throws ValidationException {
		validateUser(user);
		userAccess.createUser(user);
	}

	private void validateUser(User user) throws ValidationException {
		if (user.getName().length() < 10 && user.getMobileNumber().length() < 10) {
			throw new ValidationException();
		}
	}

}
