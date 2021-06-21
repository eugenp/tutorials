package com.baeldung.hexarch.adapters;

import com.baeldung.hexarch.domain.Ticket;
import com.baeldung.hexarch.ports.TicketRepositoryPort;
import com.baeldung.hexarch.service.TicketServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceAdapter implements TicketServicePort {

    @Autowired
    private TicketRepositoryPort ticketRepository;

    @Override
    public String createTicket(Ticket ticket) {
        return ticketRepository.bookTicket(ticket);
    }

    @Override
    public Ticket retrieveTicket(String Id) {
        return ticketRepository.fetchTicketDetails(Id);
    }

    @Override
    public Boolean cancelTicket(String Id) {
        return ticketRepository.cancelTicket(Id);
    }
}
