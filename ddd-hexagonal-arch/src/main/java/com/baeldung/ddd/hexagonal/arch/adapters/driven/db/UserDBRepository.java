package com.baeldung.ddd.hexagonal.arch.adapters.driven.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDBRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
}
