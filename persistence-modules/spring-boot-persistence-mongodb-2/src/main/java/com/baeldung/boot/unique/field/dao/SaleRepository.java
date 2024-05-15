package com.baeldung.boot.unique.field.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.boot.unique.field.data.Sale;
import com.baeldung.boot.unique.field.data.SaleId;

public interface SaleRepository extends MongoRepository<Sale, String> {
    Optional<Sale> findBySaleId(SaleId id);
}
