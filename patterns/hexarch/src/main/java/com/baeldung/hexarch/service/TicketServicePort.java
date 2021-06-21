package com.baeldung.hexarch.service;

import com.baeldung.hexarch.domain.Ticket;

public interface TicketServicePort {

    String createTicket(Ticket ticket);

    Ticket retrieveTicket(String Id);

    Boolean cancelTicket(String Id);
}
