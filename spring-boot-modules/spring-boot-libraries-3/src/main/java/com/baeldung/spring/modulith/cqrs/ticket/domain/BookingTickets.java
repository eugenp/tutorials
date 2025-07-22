package com.baeldung.spring.modulith.cqrs.ticket.domain;

import org.jmolecules.architecture.cqrs.Command;
import org.jmolecules.architecture.cqrs.CommandHandler;

public interface BookingTickets {

    @CommandHandler
    Long bookTicket(BookTicket bookTicket);

    @CommandHandler
    Long cancelTicket(CancelTicket cancelTicket);

    @Command
    record BookTicket(Long movieId, String seat) {
    }

    @Command
    record CancelTicket(Long bookingId) {
    }

}
