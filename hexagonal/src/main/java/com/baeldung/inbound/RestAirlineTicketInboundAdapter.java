package com.baeldung.inbound;

import com.baeldung.core.AirlineService;
import com.baeldung.core.AirlineTicket;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
public class RestAirlineTicketInboundAdapter implements AirlineTicketInboundPort {

    private AirlineService airlineService;

    public RestAirlineTicketInboundAdapter(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @Override
    @PostMapping(path = "/search/tickets/{from}/{to}/{fromCountry}/{toCountry}")
    public List<AirlineTicket> search(@PathVariable("from") Long from,
                                      @PathVariable("to") Long to,
                                      @PathVariable("fromCountry") String fromCountry,
                                      @PathVariable("toCountry") String toCountry) {
        LocalDate fromDate = Instant.ofEpochMilli(from).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate toDate = Instant.ofEpochMilli(to).atZone(ZoneId.systemDefault()).toLocalDate();

        return airlineService.search(fromDate, toDate, fromCountry, toCountry);
    }
}
