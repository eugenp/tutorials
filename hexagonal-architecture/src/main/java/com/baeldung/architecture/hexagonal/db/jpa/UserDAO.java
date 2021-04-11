package com.baeldung.architecture.hexagonal.db.jpa;

import com.baeldung.architecture.hexagonal.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<UserEntity, Integer> {
}
