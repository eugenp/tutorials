package com.baeldung.hexagonalarchitectureinjava.outputport;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataMongoOrderEntityRepository extends MongoRepository<OrderEntity, UUID> {
}
