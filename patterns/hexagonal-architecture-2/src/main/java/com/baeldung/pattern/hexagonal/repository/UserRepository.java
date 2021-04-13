package com.baeldung.pattern.hexagonal.repository;

import com.baeldung.pattern.hexagonal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> getByLogin(String login);
}