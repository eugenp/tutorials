package com.baeldung.httpfirewall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HttpFirewallApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(HttpFirewallApplication.class);
        application.setAdditionalProfiles("httpfirewall");
        application.run(args);
    }

}
