package com.baeldung.hexarch.adapters;

import com.baeldung.hexarch.domain.Ticket;
import com.baeldung.hexarch.ports.TicketRepositoryPort;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepositoryInMemoryAdapter implements TicketRepositoryPort {

    Map<String, Ticket> ticketMap = new HashMap();

    @Override
    public String bookTicket(Ticket ticket) {
        // generate random ticket ID
        String ticketId = UUID.randomUUID()
            .toString();
        ticket.setTicketId(ticketId);
        ticketMap.put(ticketId, ticket);
        return ticketId;
    }

    @Override
    public Boolean cancelTicket(String ticketId) {
        if (ticketMap.containsKey(ticketId)) {
            ticketMap.remove(ticketId);
            return true;
        }
        return false;
    }

    @Override
    public Ticket fetchTicketDetails(String ticketId) {
        return ticketMap.get(ticketId);
    }
}
