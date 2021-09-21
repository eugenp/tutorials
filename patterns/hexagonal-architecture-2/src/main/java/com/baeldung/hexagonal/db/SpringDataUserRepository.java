package com.baeldung.hexagonal.db;

import com.baeldung.hexagonal.db.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataUserRepository extends CrudRepository<UserEntity, Long> {
}
