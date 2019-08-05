package com.baeldung.adapter;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import com.baeldung.hexagon.adapter.AdapterFactory;
import com.baeldung.hexagon.model.Ticket;
import com.baeldung.hexagon.port.MovieTicketBookingPort;

public class AdapterUnitTest {

    @Test
    public void whenMovieProvided_thenTicketsReturned() {
        String movie = "Spider Man";

        MovieTicketBookingPort adapter = AdapterFactory.getAdapter("Movie");
        assertNotNull(adapter);

        LocalDateTime movieTime = LocalDateTime.now().plusHours(3);
        List<Ticket> tickets = adapter.bookTickets(movie, movieTime, 5);
        assertEquals(5, tickets.size());

        for (Ticket ticket : tickets) {
            assertAll(
                    () -> assertEquals(movie, ticket.getMovie()),
                    () -> assertEquals(movieTime, ticket.getTimestamp())
            );
        }
    }

    @Test
    public void whenWrongPortTypeProvided_ThenNoAdapterReturned() {
        MovieTicketBookingPort adapter = AdapterFactory.getAdapter("Test");
        assertNull(adapter);
    }
}
