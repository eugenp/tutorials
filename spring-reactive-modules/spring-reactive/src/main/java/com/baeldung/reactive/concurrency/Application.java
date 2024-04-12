package com.baeldung.reactive.concurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* Please note we assume Mongo and Kafka are running in the local machine and on default configuration.
* Additionally, if you want to experiment with Tomcat/Jetty instead of Netty, just uncomment the lines in pom.xml and rebuild.
*/
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
