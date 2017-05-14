package org.baeldung.bootTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class BootTestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootTestServiceApplication.class, args);
    }

}
