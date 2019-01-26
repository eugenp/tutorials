package com.baeldung.webjar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.autoconfiguration.MySQLAutoconfiguration;

@SpringBootApplication(exclude = MySQLAutoconfiguration.class)
public class WebjarsdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebjarsdemoApplication.class, args);
    }
}
