package com.baeldung.handlerinterceptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.baeldung.handlerinterceptor")
public class HandlerInterceptorApp {
    public static void main(String[] args) {
        SpringApplication.run(HandlerInterceptorApp.class, args);
    }
}