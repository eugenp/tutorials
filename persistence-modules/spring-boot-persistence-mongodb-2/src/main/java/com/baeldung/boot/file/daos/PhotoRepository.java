package com.baeldung.boot.file.daos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.mongodb.file.models.Photo;

public interface PhotoRepository extends MongoRepository<Photo, String> {

}
