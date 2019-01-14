package com.baeldung.hexagonal.adapter.data.repository;

import com.baeldung.hexagonal.adapter.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
