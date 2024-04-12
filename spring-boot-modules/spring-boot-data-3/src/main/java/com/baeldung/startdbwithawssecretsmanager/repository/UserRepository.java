package com.baeldung.startdbwithawssecretsmanager.repository;

import com.baeldung.startdbwithawssecretsmanager.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends CrudRepository<UserEntity, Long> {
}
