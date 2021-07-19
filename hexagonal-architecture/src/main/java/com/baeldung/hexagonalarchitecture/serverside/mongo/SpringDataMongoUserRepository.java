package com.baeldung.hexagonalarchitecture.serverside.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataMongoUserRepository extends MongoRepository<MongoUser, UUID> {
}
