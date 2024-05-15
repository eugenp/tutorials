package com.baeldung.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

import static java.lang.Boolean.FALSE;

@SpringBootApplication(exclude = {
  SecurityAutoConfiguration.class,
  ManagementWebSecurityAutoConfiguration.class}
)
public class StartupTrackingApplication {

    public static void main(String[] args) {
        // only load properties for this application
        System.setProperty("spring.config.location", "classpath:application-startup.properties");

        SpringApplication app = new SpringApplication(StartupTrackingApplication.class);
        BufferingApplicationStartup startup = new BufferingApplicationStartup(2048);

        if (shouldFilterSteps()) {
            startup.addFilter(startupStep -> startupStep.getName().matches("spring.beans.instantiate"));
        }

        app.setApplicationStartup(startup);
        app.run(args);
    }

    private static boolean shouldFilterSteps() {
        return Boolean.parseBoolean(
          System.getProperty("startup.steps.filter", FALSE.toString())
        );
    }

}
