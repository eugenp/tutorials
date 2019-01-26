package org.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({ "org.baeldung.config", "org.baeldung.persistence", "org.baeldung.security", "org.baeldung.web" })
// @ComponentScan(excludeFilters = { @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.baeldung.voter.*"), @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.baeldung.multipleauthproviders.*"),
// @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.baeldung.multiplelogin.*"), @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.baeldung.multipleentrypoints.*"),
// @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.baeldung.rolesauthorities.*"), @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.baeldung.acl.*") })
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
