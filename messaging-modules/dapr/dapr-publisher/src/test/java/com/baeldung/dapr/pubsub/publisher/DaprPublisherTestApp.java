package com.baeldung.dapr.pubsub.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplication.Running;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.testcontainers.Testcontainers;

@SpringBootApplication
public class DaprPublisherTestApp {

    public static void main(String[] args) {
        Running app = SpringApplication.from(DaprPublisherApp::main)
            .with(DaprTestContainersConfig.class)
            .run(args);

        int port = app.getApplicationContext()
            .getEnvironment()
            .getProperty("server.port", Integer.class);
        Testcontainers.exposeHostPorts(port);
    }
}
