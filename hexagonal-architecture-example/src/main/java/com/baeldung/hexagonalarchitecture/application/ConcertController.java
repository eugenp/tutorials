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

import com.baeldung.hexagonalarchitecture.application.response.AddTicketResponse;
import com.baeldung.hexagonalarchitecture.application.response.CreateConcertResponse;
import com.baeldung.hexagonalarchitecture.application.response.GetConcertsResponse;
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
    GetConcertsResponse getConcerts() {
        final List<Concert> allConcerts = concertService.getAllConcerts();
        return new GetConcertsResponse(allConcerts);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    CreateConcertResponse createConcert(@RequestBody final Concert concert) {
        final UUID id = concertService.addConcert(concert);

        return new CreateConcertResponse(id);
    }

    @DeleteMapping
    void deleteConcert(@RequestParam final UUID concertId) {
        concertService.deleteConcert(concertId);
    }

    @PostMapping(value = "/{concertId}/tickets", consumes = MediaType.APPLICATION_JSON_VALUE)
    AddTicketResponse addTicket(@PathVariable final UUID concertId, @RequestBody final Ticket ticket) {
        final UUID ticketId = concertService.addTicket(concertId, ticket);

        return new AddTicketResponse(ticketId);
    }

    @DeleteMapping(value = "/{concertId}/tickets", consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteTicket(@PathVariable final UUID concertId, @RequestParam final UUID ticketId) {
        concertService.deleteTicket(concertId, ticketId);
    }
}
