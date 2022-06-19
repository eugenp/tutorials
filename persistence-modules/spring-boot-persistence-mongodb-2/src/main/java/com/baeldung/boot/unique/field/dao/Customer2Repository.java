package com.baeldung.boot.unique.field.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.boot.unique.field.data.Customer2;

public interface Customer2Repository extends MongoRepository<Customer2, Long> {
}
