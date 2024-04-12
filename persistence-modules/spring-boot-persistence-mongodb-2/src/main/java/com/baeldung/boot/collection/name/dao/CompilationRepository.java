package com.baeldung.boot.collection.name.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.boot.collection.name.data.Compilation;

public interface CompilationRepository extends MongoRepository<Compilation, String> {

}
