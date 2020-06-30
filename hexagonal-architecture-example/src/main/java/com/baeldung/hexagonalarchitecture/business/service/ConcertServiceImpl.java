package com.baeldung.hexagonalarchitecture.business.service;

import java.util.List;
import java.util.UUID;

import com.baeldung.hexagonalarchitecture.business.entities.Concert;
import com.baeldung.hexagonalarchitecture.business.entities.Ticket;
import com.baeldung.hexagonalarchitecture.business.repository.ConcertRepository;


public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;

    public ConcertServiceImpl(final ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    @Override
    public UUID addConcert(final Concert concert) {
        return concertRepository.save(concert);
    }

    @Override
    public void deleteConcert(final UUID id) {
        concertRepository.delete(id);
    }

    @Override
    public UUID addTicket(final UUID concertId, final Ticket ticket) {
        final Concert concert = getConcert(concertId);
        concert.addTicket(ticket);

        concertRepository.save(concert);

        return ticket.getId();
    }

    @Override
    public void deleteTicket(final UUID concertId, final UUID ticketId) {
        final Concert concert = getConcert(concertId);
        concert.removeTicket(ticketId);

        concertRepository.save(concert);
    }

    @Override
    public Concert getConcert(final UUID id) {
        return concertRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Concert with ID: '" + id + "' does not exist"));
    }

    @Override
    public List<Concert> getAllConcerts() {
        return concertRepository.findAll();
    }
}
