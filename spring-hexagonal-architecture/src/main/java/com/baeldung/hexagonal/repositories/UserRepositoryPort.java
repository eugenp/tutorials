package com.baeldung.hexagonal.repositories;

import com.baeldung.hexagonal.models.User;

public interface UserRepositoryPort {

	public boolean addUser(User user);
}
