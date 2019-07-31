package org.baeldung.ip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class IpApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(IpApplication.class, args);
    }
}
