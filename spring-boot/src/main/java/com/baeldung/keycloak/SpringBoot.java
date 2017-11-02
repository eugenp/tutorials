package com.baeldung.keycloak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.baeldung.autoconfiguration.MySQLAutoconfiguration;

@SpringBootApplication(exclude = MySQLAutoconfiguration.class)

public class SpringBoot {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot.class, args);
}

}
