package com.baeldung.hexarch.ports;

import com.baeldung.hexarch.domain.Ticket;

public interface TicketRepositoryPort {

    String bookTicket(Ticket ticket);

    Boolean cancelTicket(String ticketId);

    Ticket fetchTicketDetails(String ticketId);
}
