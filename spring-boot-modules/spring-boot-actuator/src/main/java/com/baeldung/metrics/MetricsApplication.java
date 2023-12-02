package com.baeldung.metrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.SecurityRequestMatchersManagementContextConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.request.RequestContextListener;

import jakarta.servlet.ServletContext;

@EnableScheduling
@ComponentScan("com.baeldung.metrics")
@SpringBootApplication
public class MetricsApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MetricsApplication.class);
    }

    @Override
    public void onStartup(ServletContext sc) {
        // Manages the lifecycle of the root application context
        sc.addListener(new RequestContextListener());
    }

    public static void main(final String[] args) {
        // only load properties for this application
        System.setProperty("spring.config.location", "classpath:application-metrics.properties");
        SpringApplication.run(MetricsApplication.class, args);
    }

}