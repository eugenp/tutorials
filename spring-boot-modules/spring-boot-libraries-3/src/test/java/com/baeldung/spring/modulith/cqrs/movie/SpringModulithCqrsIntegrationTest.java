package com.baeldung.spring.modulith.cqrs.movie;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({ "cqrs", "h2" })
class SpringModulithCqrsIntegrationTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void whenWeVerifyModuleStructure_thenThereAreNoUnwantedDependencies() {
        ApplicationModules modules = ApplicationModules.of("com.baeldung.spring.modulith.cqrs")
            .verify();
    }

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
                .content("""
                    {
                        "movieId": %s,
                        "seat": "%s"
                    }
                    """.formatted(movieId, seat)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        return objectMapper.readTree(json)
            .get("bookingId")
            .asLong();
    }

    private Long sendCancelTicketRequest(Long bookingId) throws Exception {
        String json = mockMvc.perform(delete("/api/ticket-booking/" + bookingId))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        return objectMapper.readTree(json)
            .get("cancellationId")
            .asLong();
    }

    private AvailableMovieSeats findAvailableSeats(Long movieId) throws Exception {
        String json = mockMvc.perform(get("/api/movies/%s/seats".formatted(movieId)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        return objectMapper.readValue(json, AvailableMovieSeats.class);
    }

    private void theSeatShouldEventuallyBeFree(long testMovieId, String testSeat) {
        await().atMost(ofSeconds(5))
            .pollInterval(ofMillis(200))
            .untilAsserted(() -> assertThat(findAvailableSeats(testMovieId).freeSeats()).contains(testSeat));
    }

    private void theSeatShouldEventuallyBeOccupied(long testMovieId, String testSeat) {
        await().atMost(ofSeconds(5))
            .pollInterval(ofMillis(200))
            .untilAsserted(() -> assertThat(findAvailableSeats(testMovieId).freeSeats()).doesNotContain(testSeat));
    }

}
