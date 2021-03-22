package com.baeldung.inbound;


import com.baeldung.core.AirlineTicket;

import java.util.List;

public interface AirlineTicketInboundPort {
    List<AirlineTicket> search(Long from, Long to, String fromCountry, String toCountry);
}
