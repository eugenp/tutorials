package com.baeldung.constructor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FlightRetreiverApp {

    public static void main(String[] args) {

        ApplicationContext appContext = new ClassPathXmlApplicationContext("flights-constructor.xml");
        Reservation flightReservation = (Reservation) appContext.getBean("reservation");
        flightReservation.displayFlight();

    }

}



