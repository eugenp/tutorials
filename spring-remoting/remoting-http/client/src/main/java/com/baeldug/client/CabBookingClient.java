package com.baeldug.client;

import com.baeldung.api.BookingException;
import com.baeldung.api.CabBookingService;

public class CabBookingClient {

    private CabBookingService cabService;

    public CabBookingClient(CabBookingService cabService) {
        this.cabService = cabService;
    }

    public void run() throws BookingException {
        System.out.println( cabService.bookPickUp("13 Seagate Blvd, Key Largo, FL 33037") );
    }

}
