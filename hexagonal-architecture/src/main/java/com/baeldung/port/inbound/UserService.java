package com.baeldung.port.inbound;

import java.util.Collection;

import com.baeldung.core.domain.User;

public interface UserService {
	String addNewUser(User user);

	User getUser(String id);

	boolean removeUser(String id);

	Collection<User> getAllUser();
}
