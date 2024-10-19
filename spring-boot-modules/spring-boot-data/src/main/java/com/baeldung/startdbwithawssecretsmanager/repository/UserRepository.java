package com.baeldung.startdbwithawssecretsmanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.startdbwithawssecretsmanager.model.UserEntity;

@Repository
public interface UserRepository
        extends CrudRepository<UserEntity, Long> {
}
