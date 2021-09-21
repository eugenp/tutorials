package com.baeldung.hexagonal.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.db.entity.UserEntity;

@Repository
public interface SpringDataUserRepository extends CrudRepository<UserEntity, Long> {
}
