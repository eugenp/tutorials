package com.baeldung.hexagon.app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.baeldung.hexagon.model.Ticket;
import com.baeldung.hexagon.port.MovieTicketBookingPort;

public class MovieTicketBookingSystem implements MovieTicketBookingPort {

	@Override
	public List<Ticket> bookTickets(String movie, LocalDateTime timestamp, int seatCount) {
		List<Ticket> tickets = new ArrayList<>(seatCount);
		for (int i = 0; i < seatCount; i++) {
			Ticket ticket = new Ticket(movie, timestamp);
			tickets.add(ticket);
		}
		return tickets;
	}
}
