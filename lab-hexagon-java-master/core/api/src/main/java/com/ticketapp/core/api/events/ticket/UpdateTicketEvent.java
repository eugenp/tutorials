package com.ticketapp.core.api.events.ticket;

import com.ticketapp.core.api.events.UpdateEvent;
import com.ticketapp.core.api.objects.TicketDetails;

public class UpdateTicketEvent extends UpdateEvent<TicketDetails, UpdateTicketEvent> {

	public UpdateTicketEvent(TicketDetails ticket) {
		setObject(ticket);
	}

}
