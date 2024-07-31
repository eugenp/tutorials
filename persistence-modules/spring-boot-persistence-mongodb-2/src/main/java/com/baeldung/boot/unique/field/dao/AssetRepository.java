package com.baeldung.boot.unique.field.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.boot.unique.field.data.Asset;

public interface AssetRepository extends MongoRepository<Asset, String> {
}
