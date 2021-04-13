package com.baeldung.port.outbound;

import java.util.Collection;

import com.baeldung.core.domain.User;

public interface UserRepository {
	String addNewUser(User user);

	User getUser(String id);

	boolean removeUser(String id);

	Collection<User> getAllUser();
}
