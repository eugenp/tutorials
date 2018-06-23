package com.baeldung.reactive.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Collections;

@SpringBootApplication
public class SmsAlertReactiveClientApplication {

    public static void main (String ...params) {
        new SpringApplicationBuilder(SmsAlertReactiveClientApplication.class)
                .properties(Collections.singletonMap("server.port", "8081"))
                .run(params);
    }
}
