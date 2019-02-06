package com.ticketapp.core.application.handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ticketapp.core.api.TicketService;
import com.ticketapp.core.api.events.ticket.TicketsReadEvent;
import com.ticketapp.core.api.events.ticket.CreateTicketEvent;
import com.ticketapp.core.api.events.ticket.DeleteTicketEvent;
import com.ticketapp.core.api.events.ticket.ReadTicketsEvent;
import com.ticketapp.core.api.events.ticket.TicketCreatedEvent;
import com.ticketapp.core.api.events.ticket.TicketDeletedEvent;
import com.ticketapp.core.api.events.ticket.TicketUpdatedEvent;
import com.ticketapp.core.api.events.ticket.UpdateTicketEvent;
import com.ticketapp.core.api.objects.TicketDetails;
import com.ticketapp.core.application.domain.Account;
import com.ticketapp.core.application.domain.Ticket;
import com.ticketapp.core.application.repository.AccountRepository;
import com.ticketapp.core.application.repository.TicketRepository;

/**
 * Internal implementation of the {@link TicketService}. This class should not
 * be accessed directly.
 */
@Service
@Transactional
public class TicketServiceHandler implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public TicketsReadEvent list(ReadTicketsEvent event) {
		TicketsReadEvent outputEvt = new TicketsReadEvent();

		List<Ticket> entities = ticketRepository.findAll();

		if (entities.isEmpty()) {
			return new TicketsReadEvent().notFound();
		}

		List<TicketDetails> output = new ArrayList<TicketDetails>();
		for (Ticket entity : entities) {
			output.add(entity.toTicketDetails());
		}

		return outputEvt;

	}

	@Override
	public TicketCreatedEvent create(CreateTicketEvent event) {

		Ticket entity = Ticket.fromTicketDetails(event.getObject());

		// Use case rules go here
		// Use service composition to call other services
		entity.setCreation(new Date());
		Account account = accountRepository.findOne(event.getObject().getAccount());
		entity.setAccount(account);
		entity = ticketRepository.save(entity);

		return new TicketCreatedEvent(entity.toTicketDetails());
	}

	@Override
	public TicketUpdatedEvent update(UpdateTicketEvent event) {
		Ticket entity = Ticket.fromTicketDetails(event.getObject());
		entity = ticketRepository.save(entity);
		return new TicketUpdatedEvent(entity.toTicketDetails());
	}

	@Override
	public TicketDeletedEvent delete(DeleteTicketEvent ticket) {
		try {
			ticketRepository.delete(ticket.getCode());
		} catch (EmptyResultDataAccessException e) {
			return new TicketDeletedEvent().notFound();
		}
		return new TicketDeletedEvent();
	}

}
