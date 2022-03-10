package com.baeldung.hexagonalarchitecture.infrastructure.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJPA extends JpaRepository<UserEntity, String> {
}
