package com.baeldung.boot.composite.key.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.boot.composite.key.data.Sale;
import com.baeldung.boot.composite.key.data.TicketId;

public interface SaleRepository extends MongoRepository<Sale, String> {
    Optional<Sale> findByTicketId(TicketId ticketId);
}
