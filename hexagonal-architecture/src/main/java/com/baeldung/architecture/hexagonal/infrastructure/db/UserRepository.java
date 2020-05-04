package com.baeldung.architecture.hexagonal.infrastructure.db;

import com.baeldung.architecture.hexagonal.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNameEquals(String name);
}
