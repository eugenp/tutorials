package org.baeldung.repository;

import org.baeldung.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    //
}
