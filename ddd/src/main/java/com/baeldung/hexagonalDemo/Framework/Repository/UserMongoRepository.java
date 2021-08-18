package com.hexagon.hexagon_architecture.Framework.Repository;

import java.util.Optional;

import com.hexagon.hexagon_architecture.Domain.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, String> {

    Optional<User> findByUserId(String userId);
}
