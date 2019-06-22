package com.baeldung.mongodb.daos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.mongodb.models.Photo;

public interface PhotoRepository extends MongoRepository<Photo, String> {

}
