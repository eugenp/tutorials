package com.baeldung.hexarch;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.baeldung.hexarch.adapters.TicketController;
import com.baeldung.hexarch.domain.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HexarchApplicationTests {

    @Autowired
    private TicketController ticketController;

    private Ticket ticket;

    @BeforeEach
    void setup() {
        ticket = new Ticket("Adam Baer", "12/02/1986", "", "New York", "Los Angeles");
    }

    @Test
    void givenTicket_whenCreated_shouldReturnId() {
        String ticketId = ticketController.createTicket(ticket);

        assertThat(ticketId).isNotEmpty();
    }

    @Test
    void givenTicketId_whenRetrieved_shouldReturnTicketDetails() {
        String ticketId = ticketController.createTicket(ticket);

        Ticket ticketDetails = ticketController.getTicketDetails(ticketId);

        assertThat(ticketDetails).isNotNull();
        assertThat(ticketDetails.getTicketId()).isEqualTo(ticketId);
    }

    @Test
    void givenTicketId_whenDeleted_shouldReturnSuccessMessage() {
        String ticketId = ticketController.createTicket(ticket);

        String ticketCancelMessage = ticketController.cancelTicket(ticketId);

        assertThat(ticketCancelMessage).isEqualTo("Ticket Cancelled");
    }

}
