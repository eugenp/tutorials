package com.baeldung.hexagonalarchitecture.application;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonalarchitecture.business.entities.Concert;
import com.baeldung.hexagonalarchitecture.business.entities.Ticket;
import com.baeldung.hexagonalarchitecture.business.service.ConcertService;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    private final ConcertService concertService;

    @Autowired
    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Concert> getConcerts() {
        return concertService.getAllConcerts();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    UUID createConcert(@RequestBody final Concert concert) {
        final UUID id = concertService.addConcert(concert);

        return id;
    }

    @DeleteMapping
    void deleteConcert(@RequestParam final UUID concertId) {
        concertService.deleteConcert(concertId);
    }

    @PostMapping(value = "/{concertId}/tickets", consumes = MediaType.APPLICATION_JSON_VALUE)
    UUID addTicket(@PathVariable final UUID concertId, @RequestBody final Ticket ticket) {
        final UUID ticketId = concertService.addTicket(concertId, ticket);
        return ticketId;
    }

    @DeleteMapping(value = "/{concertId}/tickets", consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteTicket(@PathVariable final UUID concertId, @RequestParam final UUID ticketId) {
        concertService.deleteTicket(concertId, ticketId);
    }
}
