package com.baeldung.swagger2boot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.swagger2boot.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
}
