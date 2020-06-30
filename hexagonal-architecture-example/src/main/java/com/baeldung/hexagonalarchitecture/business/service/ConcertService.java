package com.baeldung.hexagonalarchitecture.business.service;

import java.util.List;
import java.util.UUID;

import com.baeldung.hexagonalarchitecture.business.entities.Concert;
import com.baeldung.hexagonalarchitecture.business.entities.Ticket;

public interface ConcertService {

    UUID addConcert(final Concert concert);

    void deleteConcert(final UUID id);

    UUID addTicket(final UUID concertId, final Ticket ticket);

    void deleteTicket(final UUID concertId, final UUID ticketId);

    Concert getConcert(final UUID id);

    List<Concert> getAllConcerts();

}
