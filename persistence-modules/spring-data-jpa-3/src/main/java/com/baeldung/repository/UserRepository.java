package com.baeldung.repository;

import com.baeldung.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "permissions")
    Optional<User> findDetailedByUsername(String username);

    Optional<User> findSummaryByUsername(String username);

    Optional<User> findByUsername(String username);
}
