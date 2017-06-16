package com.baeldung.setter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class FlightRetrieverApp {

    public static void main(String[] args) {
        
        ApplicationContext appContext = new ClassPathXmlApplicationContext("flights-setter.xml");
        Reservation flightReservation = (Reservation) appContext.getBean("reservation");
        flightReservation.displayFlight();

    }

}
