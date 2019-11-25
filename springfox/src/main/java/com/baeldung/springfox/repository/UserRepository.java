package com.baeldung.springfox.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.springfox.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
}
