package com.baeldung.spring.session.tomcatex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class JettyWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(JettyWebApplication.class, args);
    }

    @RequestMapping
    public String helloJetty() {
        return "hello Jetty";
    }

    @RequestMapping("/test")
    public String lksjdf() {
        return "";
    }
}
