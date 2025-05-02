package com.baeldung.dapr.pubsub.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplication.Running;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DaprSubscriberTestApp {

    private static final Logger logger = LoggerFactory.getLogger(DaprSubscriberTestApp.class);

    public static void main(String[] args) {
        Running app = SpringApplication.from(DaprSubscriberApp::main)
            .with(DaprTestContainersConfig.class)
            .run(args);

        int port = app.getApplicationContext()
            .getEnvironment()
            .getProperty("server.port", Integer.class);

        logger.info("[bael] exposing port {}", port);
        org.testcontainers.Testcontainers.exposeHostPorts(port);
    }
}
