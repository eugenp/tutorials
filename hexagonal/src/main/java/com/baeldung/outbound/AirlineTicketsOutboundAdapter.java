package com.baeldung.outbound;


import com.baeldung.core.AirlineTicket;

import java.time.LocalDate;
import java.util.List;

public class AirlineTicketsOutboundAdapter implements AirlineTicketsOutboundPort {

    @Override
    public List<AirlineTicket> search(LocalDate from, LocalDate to, String fromCountry, String toCountry) {
        // TODO implement fetching airline tickets from airline websites
        throw new UnsupportedOperationException();
    }
}
