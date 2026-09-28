package com.baeldung.forwardheaders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.baeldung.forwardheaders")
public class ForwardHeadersApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForwardHeadersApplication.class, args);
    }
}
