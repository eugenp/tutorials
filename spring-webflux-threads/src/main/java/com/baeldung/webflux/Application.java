package com.baeldung.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* Please note that this assumes Mongo and Kafka to be running on the local machine on default configurations.
* If you want to experiment with Tomcat/Jetty instead of Netty, just uncomment the lines in pom.xml and rebuild.
*/
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
