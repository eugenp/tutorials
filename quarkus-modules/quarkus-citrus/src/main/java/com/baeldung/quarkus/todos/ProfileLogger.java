package com.baeldung.quarkus.todos;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ConfigUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ProfileLogger {

    void onStart(@Observes StartupEvent ev) {
        log.info("The application is starting with profile " + ConfigUtils.getProfiles());
    }
}