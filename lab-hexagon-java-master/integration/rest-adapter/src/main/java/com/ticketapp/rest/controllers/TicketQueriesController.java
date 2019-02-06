package com.ticketapp.rest.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ticketapp.core.api.TicketService;
import com.ticketapp.core.api.events.ticket.TicketsReadEvent;
import com.ticketapp.core.api.events.ticket.ReadTicketsEvent;
import com.ticketapp.core.api.objects.TicketDetails;
import com.ticketapp.rest.domain.Ticket;

@Controller
@RequestMapping(value = "/api/v1/tickets")
public class TicketQueriesController {

	@Autowired
	private TicketService ticketService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Collection<Ticket> getAll() {
		List<Ticket> tickets = new ArrayList<Ticket>();
		TicketsReadEvent output = ticketService.list(new ReadTicketsEvent());

		for (TicketDetails ticket : output.getObject()) {
			tickets.add(Ticket.fromTicketDetails(ticket));
		}
		return tickets;
	}

}
