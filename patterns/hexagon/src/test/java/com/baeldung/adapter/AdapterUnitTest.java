package com.baeldung.adapter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import com.baeldung.hexagon.adapter.MovieTicketBookingAdapter;
import com.baeldung.hexagon.model.Ticket;
import com.baeldung.hexagon.port.MovieTicketBookingPort;
import com.baeldung.hexagon.port.PortFactory;

public class AdapterUnitTest {

    @Test
    public void whenMovieProvided_thenTicketsReturned() {
        String movie = "Spider Man";

        MovieTicketBookingPort port = PortFactory.getPort("Movie");
        assertNotNull(port);

        MovieTicketBookingAdapter adapter = new MovieTicketBookingAdapter(port);

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
    public void whenWrongPortTypeProvided_ThenNoPortReturned() {
        MovieTicketBookingPort port = PortFactory.getPort("Test");
        assertNull(port);
    }
}
