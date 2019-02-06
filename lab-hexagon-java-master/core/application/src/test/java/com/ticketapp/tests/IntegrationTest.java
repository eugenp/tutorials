package com.ticketapp.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ticketapp.core.api.TicketService;
import com.ticketapp.core.api.events.ResponseCode;
import com.ticketapp.core.api.events.ticket.CreateTicketEvent;
import com.ticketapp.core.api.events.ticket.TicketCreatedEvent;
import com.ticketapp.core.api.objects.TicketDetails;
import com.ticketapp.core.application.TicketappApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TicketappApplication.class)
public class IntegrationTest {

	@Autowired
	private TicketService ticketService;

	@Test
	public void testCreateTicket() {

		TicketDetails ticket = new TicketDetails();
		ticket.setAccount("root");
		ticket.setPriority(5);

		CreateTicketEvent event = new CreateTicketEvent(ticket);

		TicketCreatedEvent evtOutput = ticketService.create(event);

		assertEquals(ResponseCode.OK, evtOutput.getCode());
		
		TicketDetails output = evtOutput.getObject();

		assertEquals(5, output.getPriority());
		assertTrue(output.getCode() > 0);
	}

}
