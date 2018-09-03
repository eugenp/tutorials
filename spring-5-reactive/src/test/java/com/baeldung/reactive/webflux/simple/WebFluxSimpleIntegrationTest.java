package com.baeldung.reactive.webflux.simple;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebFluxSimpleIntegrationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebFluxSimpleIntegrationTest.class);
    private static final int SERVER_PORT = 8899;
    
    @BeforeAll
    public static void launchServer() {
        // run the server in a thread
        System.setProperty("server.port", String.valueOf(SERVER_PORT));
        new Thread(() -> {
            try {
                SimpleWebfluxApplication.main(new String[]{});
            } catch (Exception x) {
                LOGGER.error("Server error", x);
            }
        }).start();
        // wait for service to start
        try {Thread.sleep(4000);} catch (Exception x) {}
    }

    @Test
    public void whenClientLogsEventsThenSuccess() {
        SimpleWebfluxClient client = new SimpleWebfluxClient();
        client.connectAndReceiveEvents(String.valueOf(SERVER_PORT));
        try {Thread.sleep(10000);} catch (Exception x) {}
        LOGGER.info("killing test...");
    }
}
