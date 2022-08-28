package com.baeldung.properties.log;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnvironmentPropertiesPrinter {

    private final Environment env;

    @PostConstruct
    public void logApplicationProperties() {
        log.info("************************* PROPERTIES(ENVIRONMENT) ******************************");

        log.info("{}={}", "bael.property", env.getProperty("bael.property"));
        log.info("{}={}", "app.name", env.getProperty("app.name"));
        log.info("{}={}", "app.description", env.getProperty("app.description"));

        log.info("******************************************************************************");
    }
}
