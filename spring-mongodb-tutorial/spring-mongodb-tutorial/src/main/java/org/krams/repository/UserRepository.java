package org.krams.repository;

import org.krams.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
	
	User findByUsername(String username);
}
