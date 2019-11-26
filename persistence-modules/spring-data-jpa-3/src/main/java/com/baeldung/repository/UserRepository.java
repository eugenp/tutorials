package com.baeldung.repository;

import com.baeldung.model.BasicUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<BasicUser, Long> {

    @EntityGraph(attributePaths = "permissions")
    Optional<BasicUser> findDetailedByUsername(String username);

    Optional<BasicUser> findSummaryByUsername(String username);

    Optional<BasicUser> findByUsername(String username);
}
