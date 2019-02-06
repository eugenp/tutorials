package com.ticketapp.core.api.events.ticket;

import java.util.List;

import com.ticketapp.core.api.events.ResponseEvent;
import com.ticketapp.core.api.objects.TicketDetails;

public class TicketsReadEvent extends ResponseEvent<TicketsReadEvent, List<TicketDetails>> {

}
