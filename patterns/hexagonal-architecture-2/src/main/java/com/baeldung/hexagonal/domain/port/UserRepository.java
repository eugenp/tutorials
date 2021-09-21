package com.baeldung.hexagonal.domain.port;

import com.baeldung.hexagonal.domain.model.User;

import java.util.Optional;

public interface UserRepository {
  User save(User user);
  Optional<User> findById(long id);
}
