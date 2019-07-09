package com.baeldung.hexagon.domain.ports;

import com.baeldung.hexagon.domain.model.User;

public interface IUserAccess {
	void createUser(User user);
}
