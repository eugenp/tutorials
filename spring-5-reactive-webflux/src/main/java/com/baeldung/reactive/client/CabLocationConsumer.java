/**
 * 
 */
package com.baeldung.reactive.client;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.reactive.model.CabLocation;

/**
 * @author swpraman
 *
 */
public class CabLocationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CabLocationConsumer.class);

    /**
     * Main method to consume locations of a cab as a stream 
     * @param args
     */
    public static void main(String[] args) {

        // The id of the booked cab
        String cabId = UUID.randomUUID().toString();

        // URI of the API
        String uri = "http://localhost:8080/cab/location/" + cabId;

        // @formatter:off 
        WebClient.create(uri)
                 .get()
                 .retrieve()
                 .bodyToFlux(CabLocation.class)
                 .subscribe(CabLocationConsumer::showLocation);
        //@formatter:on 

        sleepIndefinitely();
    }

    /**
     * Helper method to print location of the cab as received from stream
     * @param location
     */
    private static void showLocation(CabLocation location) {
        logger.debug("Current Location : {}", location);
    }

    /**
     * This method blocks the current thread indefinitely
     */
    private static void sleepIndefinitely() {
        try {
            while (true) {
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
