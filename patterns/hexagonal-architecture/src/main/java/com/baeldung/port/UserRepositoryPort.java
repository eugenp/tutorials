package com.baeldung.port;

import java.util.Collection;
import java.util.Optional;

import com.baeldung.domain.User;

public interface UserRepositoryPort {
	Optional<Collection<User>> findUsersBornOn(int month, int dayOfMonth);
}
