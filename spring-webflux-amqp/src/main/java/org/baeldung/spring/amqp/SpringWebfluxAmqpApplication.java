package org.baeldung.spring.amqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DestinationsConfig.class)
public class SpringWebfluxAmqpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxAmqpApplication.class, args);
    }
    
}
