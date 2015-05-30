package org.baeldung.main;

import org.baeldung.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@ComponentScan({ "org.baeldung.endpoints", "org.baeldung.service", "org.baeldung.monitor.jmx" })
public class SpringBootActuatorApplication {

    @Autowired
    private LoginService service;

    @RequestMapping("/")
    String home() {
        service.login("admin", "admin".toCharArray());
        return "TADA!!! You are in Spring Boot Actuator test application.";
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootActuatorApplication.class, args);
    }

}
