package com.baeldung.spring.modulith.cqrs.ticket.booking;

import org.jmolecules.architecture.cqrs.CommandHandler;

import com.baeldung.spring.modulith.cqrs.ticket.booking.api.BookTicket;
import com.baeldung.spring.modulith.cqrs.ticket.booking.api.CancelTicket;

public interface BookedTicketsCommandHandler {
    @CommandHandler
    Long bookTicket(BookTicket bookTicket);

    @CommandHandler
    Long cancelTicket(CancelTicket cancelTicket);
}
