package com.baeldung.hexagon.port;

import java.time.LocalDateTime;
import java.util.List;
import com.baeldung.hexagon.model.Ticket;

public interface MovieTicketBookingPort {
	List<Ticket> bookTickets(String movie, LocalDateTime timestamp, int seatCount);
}
