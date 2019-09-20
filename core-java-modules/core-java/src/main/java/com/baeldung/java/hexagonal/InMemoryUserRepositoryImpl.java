package com.baeldung.java.hexagonal;

/**
 * An in-memory implementation of the UserRepository
 */
import java.util.Collection;
import java.util.Optional;

public class InMemoryUserRepositoryImpl implements UserRepository{

	@Override
	public User save(User user) {
		return null;
	}

	@Override
	public boolean update(int userId) throws UserNotFoundException {
		return false;
	}

	@Override
	public Collection<User> findAll() {
		return null;
	}

	@Override
	public Optional<User> findById(int userId) {
		return null;
	}

}
