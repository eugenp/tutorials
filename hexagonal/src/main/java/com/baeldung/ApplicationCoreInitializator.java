package com.baeldung;


import com.baeldung.core.AirlineService;
import com.baeldung.outbound.AirlineTicketsDbOutboundAdapter;
import com.baeldung.outbound.AirlineTicketsOutboundPort;

public class ApplicationCoreInitializator {
    public void initializeApplicationCore() {
        // old configuration
        // AirlineTicketsOutboundPort airlineTicketsOutboundAdapter = new AirlineTicketsOutboundAdapter();

        AirlineTicketsOutboundPort airlineTicketsOutboundAdapter = new AirlineTicketsDbOutboundAdapter();
        AirlineService airlineService = new AirlineService(airlineTicketsOutboundAdapter);
    }
}
