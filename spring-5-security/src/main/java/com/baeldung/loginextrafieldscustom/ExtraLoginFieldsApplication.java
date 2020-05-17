package com.baeldung.loginextrafieldscustom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.baeldung.loginextrafieldscustom", "com.baeldung.dao", "com.baeldung.domain", "com.baeldung.persistence"})
public class ExtraLoginFieldsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtraLoginFieldsApplication.class, args);
    }

}
