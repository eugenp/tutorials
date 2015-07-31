package org.baeldung.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.baeldung.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}
