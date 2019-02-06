package com.ticketapp.core.api;

import com.ticketapp.core.api.events.ticket.CreateTicketEvent;
import com.ticketapp.core.api.events.ticket.DeleteTicketEvent;
import com.ticketapp.core.api.events.ticket.ReadTicketsEvent;
import com.ticketapp.core.api.events.ticket.TicketCreatedEvent;
import com.ticketapp.core.api.events.ticket.TicketDeletedEvent;
import com.ticketapp.core.api.events.ticket.TicketUpdatedEvent;
import com.ticketapp.core.api.events.ticket.TicketsReadEvent;
import com.ticketapp.core.api.events.ticket.UpdateTicketEvent;

/**
 * Ticket Service - Use Case API
 */
public interface TicketService {

	TicketsReadEvent list(ReadTicketsEvent event);

	TicketCreatedEvent create(CreateTicketEvent ticket);

	TicketUpdatedEvent update(UpdateTicketEvent ticket);

	TicketDeletedEvent delete(DeleteTicketEvent ticket);

}
