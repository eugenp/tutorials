package com.baeldung.security.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.security.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmailId(final String emailId);

}