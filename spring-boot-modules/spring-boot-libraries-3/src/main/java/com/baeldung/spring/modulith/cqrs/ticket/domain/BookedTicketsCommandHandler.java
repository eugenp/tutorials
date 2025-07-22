package com.baeldung.spring.modulith.cqrs.ticket.domain;

import org.jmolecules.architecture.cqrs.CommandHandler;

import com.baeldung.spring.modulith.cqrs.ticket.BookTicket;
import com.baeldung.spring.modulith.cqrs.ticket.CancelTicket;

public interface BookedTicketsCommandHandler {
    @CommandHandler
    Long bookTicket(BookTicket bookTicket);

    @CommandHandler
    Long cancelTicket(CancelTicket cancelTicket);
}
