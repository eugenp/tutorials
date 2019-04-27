package com.baeldung.mongodb.daos;


import com.baeldung.mongodb.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, Long> {

}
