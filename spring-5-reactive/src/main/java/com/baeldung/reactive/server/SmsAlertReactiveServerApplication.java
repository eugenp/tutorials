package com.baeldung.reactive.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmsAlertReactiveServerApplication {

    public static void main (String ...params) {
        SpringApplication.run(SmsAlertReactiveServerApplication.class, params);
    }
}
