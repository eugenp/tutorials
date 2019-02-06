package com.ticketapp.core.api.events.ticket;

import com.ticketapp.core.api.events.UpdateEvent;
import com.ticketapp.core.api.objects.TicketDetails;

public class CreateTicketEvent extends UpdateEvent<TicketDetails, CreateTicketEvent> {

	public CreateTicketEvent(TicketDetails ticket) {
		setObject(ticket);
	}

}
