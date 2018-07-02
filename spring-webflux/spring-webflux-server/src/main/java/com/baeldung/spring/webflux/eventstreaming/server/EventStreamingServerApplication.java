package com.baeldung.spring.webflux.eventstreaming.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main boot class for starting reactive webflux server.
 * @author kartiksingla
 *
 */
@SpringBootApplication
public class EventStreamingServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventStreamingServerApplication.class, args);
    }
}
