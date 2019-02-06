package com.ticketapp.core.api.events.ticket;

import com.ticketapp.core.api.events.ResponseEvent;
import com.ticketapp.core.api.objects.TicketDetails;

public class TicketUpdatedEvent extends ResponseEvent<TicketUpdatedEvent, TicketDetails> {

	public TicketUpdatedEvent(TicketDetails ticket) {
		setObject(ticket);
	}
}