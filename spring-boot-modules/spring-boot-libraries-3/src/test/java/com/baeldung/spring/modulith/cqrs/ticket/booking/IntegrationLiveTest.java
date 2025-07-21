package com.baeldung.spring.modulith.cqrs.ticket.booking;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.baeldung.spring.modulith.cqrs.movie.seating.api.AvailableSeats;
import com.baeldung.spring.modulith.cqrs.ticket.booking.TicketsController.BookingResponse;
import com.baeldung.spring.modulith.cqrs.ticket.booking.TicketsController.CancelResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles({ "cqrs", "h2" })
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class IntegrationLiveTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void givenABookedTicket_whenTheBookingIsCancelled_thenTheSeatIsFree() throws Exception {
        long testMovieId = 1L;
        String testSeat = "A1";

        long bookingId = sendBookTicketRequest(testMovieId, testSeat);
        theSeatShouldEventuallyBeOccupied(testMovieId, testSeat);

        sendCancelTicketRequest(bookingId);
        theSeatShouldEventuallyBeFree(testMovieId, testSeat);
    }

    private Long sendBookTicketRequest(Long movieId, String seat) throws Exception {
        String json = mockMvc.perform(post("/api/ticket-booking").contentType(APPLICATION_JSON)
                .content("{\"movieId\": " + movieId + ", \"seat\": \"" + seat + "\"}"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        return objectMapper.readValue(json, BookingResponse.class)
            .bookingId();
    }

    private Long sendCancelTicketRequest(Long bookingId) throws Exception {
        String json = mockMvc.perform(delete("/api/ticket-booking/" + bookingId))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        return objectMapper.readValue(json, CancelResponse.class)
            .cancellationId();
    }

    private AvailableSeats findAvailableSeats(Long movieId) throws Exception {
        String json = mockMvc.perform(get("/api/movies/" + movieId + "/seats").contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        return objectMapper.readValue(json, AvailableSeats.class);
    }

    private void theSeatShouldEventuallyBeFree(long testMovieId, String testSeat) {
        await()
            .atMost(ofSeconds(5))
            .pollInterval(ofMillis(200))
            .untilAsserted(() -> assertThat(findAvailableSeats(testMovieId).freeSeats()).contains(testSeat));
    }

    private void theSeatShouldEventuallyBeOccupied(long testMovieId, String testSeat) {
        await()
            .atMost(ofSeconds(5))
            .pollInterval(ofMillis(200))
            .untilAsserted(() -> assertThat(findAvailableSeats(testMovieId).freeSeats()).doesNotContain(testSeat));
    }

}
