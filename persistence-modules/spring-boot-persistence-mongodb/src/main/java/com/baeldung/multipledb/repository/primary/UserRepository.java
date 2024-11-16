package com.baeldung.multipledb.repository.primary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.multipledb.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
}