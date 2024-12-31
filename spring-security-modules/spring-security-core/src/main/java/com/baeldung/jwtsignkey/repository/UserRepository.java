package com.baeldung.jwtsignkey.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.jwtsignkey.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

}
