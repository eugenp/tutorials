package com.baeldug.client;

import com.baeldung.api.*;

public class Client {

    private SampleServices sampleServices;

    public Client(SampleServices sampleServices) {
        this.sampleServices = sampleServices;
    }

    public void run() {

        Address pickUp = new Address("13 Seagate Blvd, Key Largo, FL 33037", "US");
        Address dropDown = new Address("91831 Overseas Hwy, Tavernier, FL 33070", "US");
        try {
            System.out.println( sampleServices.bookPickUp(pickUp, dropDown, 3) );
        } catch (BookinkException e) {
            e.printStackTrace();
        }

    }

}
