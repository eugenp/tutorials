package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulPostFilterApplication {

    public static void main(String... args) {
        SpringApplication.run(ZuulPostFilterApplication.class, args);
    }
}
