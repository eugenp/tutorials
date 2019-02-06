package com.ticketapp.rest.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.ticketapp.core.api.TicketService;
import com.ticketapp.core.api.events.ticket.CreateTicketEvent;
import com.ticketapp.core.api.events.ticket.TicketCreatedEvent;
import com.ticketapp.rest.domain.Ticket;

@Controller
@RequestMapping(value = "/api/v1/tickets")
public class TicketCommandsController {

	@Autowired
	private TicketService ticketService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Ticket> create(@RequestBody @Valid Ticket ticket, UriComponentsBuilder builder) {

		CreateTicketEvent event = new CreateTicketEvent(ticket.toTicketDetails());

		TicketCreatedEvent outputEvt = ticketService.create(event);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/api/v1/tickets/{code}")
				.buildAndExpand(String.valueOf(outputEvt.getObject().getCode())).toUri());

		Ticket output = Ticket.fromTicketDetails(outputEvt.getObject());
		return new ResponseEntity<Ticket>(output, headers, HttpStatus.CREATED);
	}

}
