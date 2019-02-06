package com.ticketapp.core.application.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketapp.core.application.domain.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	List<Ticket> findByPriority(int priority);

	List<Ticket> findByCreationIsAfter(Date creation);

	List<Ticket> findByCreationIsAfterAndPriority(Date creation, int priority);
}
