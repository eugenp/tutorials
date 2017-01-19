package com.baeldug.client;

import com.baeldung.api.*;

public class CabBookingClient {

    private CabBookingService cabService;

    public CabBookingClient(CabBookingService cabService) {
        this.cabService = cabService;
    }

    public void run() throws BookingException {

        Address pickUp = new Address("13 Seagate Blvd, Key Largo, FL 33037", "US");
        Address dropDown = new Address("91831 Overseas Hwy, Tavernier, FL 33070", "US");
        System.out.println( cabService.bookPickUp(pickUp, dropDown, 3) );

    }

}
