package org.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringCloudRestClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudRestClientApplication.class, args);
    }
}
