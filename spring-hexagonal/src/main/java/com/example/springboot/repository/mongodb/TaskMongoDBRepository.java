package com.example.springboot.repository.mongodb;

import com.example.springboot.domain.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMongoDBRepository extends MongoRepository<Task, String> {
}
