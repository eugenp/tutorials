package com.baeldung.boot.collection.name.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.boot.collection.name.data.Store;

public interface StoreRepository extends MongoRepository<Store, String> {

}
