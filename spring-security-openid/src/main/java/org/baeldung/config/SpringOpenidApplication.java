package org.baeldung.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringOpenidApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringOpenidApplication.class, args);
    }

}
