package org.baeldung.recovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan
@EnableScheduling
@SpringBootApplication
public class SpringQuartzRecoveryApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringQuartzRecoveryApp.class);
        app.setAdditionalProfiles("recovery");
        app.run(args);
    }
}
