package com.baeldung.adapter.outbound;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.baeldung.core.domain.User;
import com.baeldung.port.outbound.UserRepository;

@Repository("mapRepo")
public class UserRepositoryMapImpl implements UserRepository {
	
	private final Map<String, User> userMap = new ConcurrentHashMap<>();

	@Override
	public String addNewUser(User user) {
		String id = UUID.randomUUID().toString();
		userMap.put(id, user);
		return id;
	}

	@Override
	public User getUser(String id) {
		return userMap.get(id);
	}

	@Override
	public boolean removeUser(String id) {
		return userMap.remove(id) != null;
	}

	@Override
	public Collection<User> getAllUser() {
		return userMap.values();
	}

}
