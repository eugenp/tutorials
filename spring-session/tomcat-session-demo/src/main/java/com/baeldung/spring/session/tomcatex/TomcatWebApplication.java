package com.baeldung.spring.session.tomcatex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TomcatWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(TomcatWebApplication.class, args);
    }
}
