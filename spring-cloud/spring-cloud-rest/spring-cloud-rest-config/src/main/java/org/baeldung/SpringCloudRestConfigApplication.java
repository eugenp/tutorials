package org.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
// @EnableEurekaClient
public class SpringCloudRestConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudRestConfigApplication.class, args);
    }

}
