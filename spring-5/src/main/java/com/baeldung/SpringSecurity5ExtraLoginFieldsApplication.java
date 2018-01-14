package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.baeldung.securityextrafields"})
@SpringBootApplication
public class SpringSecurity5ExtraLoginFieldsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurity5ExtraLoginFieldsApplication.class, args);
    }

}
