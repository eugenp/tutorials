package com.baeldung.a2a.client.orchestrator.jobscreening;

import org.springaicommunity.a2a.server.autoconfigure.A2AServerAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = A2AServerAutoConfiguration.class)
@PropertySource("classpath:application-job-screening-orchestrator.properties")
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}