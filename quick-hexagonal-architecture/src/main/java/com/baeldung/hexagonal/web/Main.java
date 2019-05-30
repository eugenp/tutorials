package com.baeldung.hexagonal.web;

import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    public static void main(String[] args) {
        final PersonResourceConfiguration config = new PersonResourceConfiguration();

        int port = Integer.valueOf(System.getProperty("hexagonal.port", "8080"));
        URI baseUri = UriBuilder.fromUri("http://localhost/")
            .port(port)
            .build();
        log.info("listening on port {}", port);
        GrizzlyHttpServerFactory.createHttpServer(baseUri, config, true);
    }

    private static Logger log = LoggerFactory.getLogger(Main.class);

}
