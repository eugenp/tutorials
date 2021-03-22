package com.baeldung.outbound;


import com.baeldung.core.AirlineTicket;

import java.time.LocalDate;
import java.util.List;

public interface AirlineTicketsOutboundPort {

     List<AirlineTicket> search(LocalDate from, LocalDate to, String fromCountry, String toCountry);
}
