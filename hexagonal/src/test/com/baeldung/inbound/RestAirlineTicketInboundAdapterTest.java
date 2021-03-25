package com.baeldung.inbound;

import com.baeldung.core.AirlineService;
import com.baeldung.core.AirlineTicket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RestAirlineTicketInboundAdapterTest {

    @InjectMocks
    private RestAirlineTicketInboundAdapter restAirlineTicketInboundAdapter;
    @Mock
    private AirlineService airlineService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(restAirlineTicketInboundAdapter).build();
    }

    @Test
    public void returnEmpty_ifNoRace() throws Exception {
        mockMvc.perform(post("/search/tickets/2000/4000/Bejin/Moscow").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void returnSortedByDate_ifRaceExist() throws Exception {
        LocalDate fromDate = Instant.ofEpochMilli(2000).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate toDate = Instant.ofEpochMilli(24000).atZone(ZoneId.systemDefault()).toLocalDate();

        List<AirlineTicket> expectedTickets = Arrays.asList(
                new AirlineTicket(
                        LocalDateTime.of(fromDate, LocalTime.of(15, 0)),
                        200,
                        "Bejin",
                        "Moscow"),
                new AirlineTicket(LocalDateTime.of(fromDate, LocalTime.of(19, 0)),
                        700,
                        "Bejin",
                        "Moscow"));


        when(airlineService.search(fromDate, toDate, "Bejin", "Moscow"))
                .thenReturn(expectedTickets);

        mockMvc.perform(post("/search/tickets/2000/24000/Bejin/Moscow").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[" +
                        "{\"date\":[1970,1,1,15,0],\"cost\":200,\"fromCity\":\"Bejin\",\"toCity\":\"Moscow\"}," +
                        "{\"date\":[1970,1,1,19,0],\"cost\":700,\"fromCity\":\"Bejin\",\"toCity\":\"Moscow\"}" +
                        "]"));
    }
}