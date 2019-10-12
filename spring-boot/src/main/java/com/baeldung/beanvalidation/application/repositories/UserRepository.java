package com.baeldung.beanvalidation.application.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.beanvalidation.application.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {}
