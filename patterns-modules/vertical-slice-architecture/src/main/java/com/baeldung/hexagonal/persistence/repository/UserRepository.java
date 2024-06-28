package com.baeldung.hexagonal.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.hexagonal.persistence.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
