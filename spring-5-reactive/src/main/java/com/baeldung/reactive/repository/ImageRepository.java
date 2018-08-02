package com.baeldung.reactive.repository;

import com.baeldung.reactive.model.Image;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends ReactiveMongoRepository<Image, String> {
}
