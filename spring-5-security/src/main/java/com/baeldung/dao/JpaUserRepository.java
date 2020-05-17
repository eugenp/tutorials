package com.baeldung.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.domain.UserEntity;

public interface JpaUserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsernameAndDomain(String username, String domain);
}
