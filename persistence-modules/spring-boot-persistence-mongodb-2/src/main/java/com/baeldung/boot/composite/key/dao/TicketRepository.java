package com.baeldung.boot.composite.key.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.boot.composite.key.data.Ticket;
import com.baeldung.boot.composite.key.data.TicketId;

public interface TicketRepository extends MongoRepository<Ticket, TicketId> {

}
