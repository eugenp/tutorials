package com.ticketapp.core.api.events.ticket;

import com.ticketapp.core.api.events.ResponseEvent;
import com.ticketapp.core.api.objects.TicketDetails;

public class TicketCreatedEvent extends ResponseEvent<TicketCreatedEvent, TicketDetails> {

	public TicketCreatedEvent(TicketDetails ticket) {
		setObject(ticket);
	}

}
