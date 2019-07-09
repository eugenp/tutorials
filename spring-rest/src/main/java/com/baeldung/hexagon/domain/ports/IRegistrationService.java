package com.baeldung.hexagon.domain.ports;

import javax.validation.ValidationException;

import com.baeldung.hexagon.domain.model.User;

public interface IRegistrationService {
	void registerUser(User user) throws ValidationException;
}
