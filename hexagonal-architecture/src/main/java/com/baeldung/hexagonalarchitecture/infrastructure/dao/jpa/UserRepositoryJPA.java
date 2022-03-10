package com.baeldung.hexagonalarchitecture.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJPA extends JpaRepository<UserEntity, Long> {
}
