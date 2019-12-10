package com.baeldung.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.model.BasicUser;

public interface UserRepository extends JpaRepository<BasicUser, Long> {

    Optional<BasicUser> findSummaryByUsername(String username);

    Optional<BasicUser> findByUsername(String username);
}
