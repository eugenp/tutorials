package com.baeldung.properties.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AppContextRefreshedEventPropertiesPrinter {

    @EventListener
    public void handleContextRefreshed(ContextRefreshedEvent event) {
        printAllActiveProperties((ConfigurableEnvironment) event.getApplicationContext().getEnvironment());

        printAllApplicationProperties((ConfigurableEnvironment) event.getApplicationContext().getEnvironment());
    }

    private void printAllActiveProperties(ConfigurableEnvironment env) {

        log.info("************************* ALL PROPERTIES(EVENT) ******************************");

        List<MapPropertySource> propertySources = env.getPropertySources()
                .stream()
                .filter(ps -> ps instanceof MapPropertySource)
                .map(ps -> (MapPropertySource) ps)
                .collect(Collectors.toList());

        printProperties(env, propertySources);
        log.info("******************************************************************************");
    }

    private void printAllApplicationProperties(ConfigurableEnvironment env) {

        log.info("************************* APP PROPERTIES(EVENT) ******************************");

        List<MapPropertySource> propertySources = env.getPropertySources()
                .stream()
                .filter(ps -> ps instanceof MapPropertySource && ps.getName().contains("application.properties"))
                .map(ps -> (MapPropertySource) ps)
                .collect(Collectors.toList());

        printProperties(env, propertySources);
        log.info("******************************************************************************");
    }

    private void printProperties(ConfigurableEnvironment env, List<MapPropertySource> propertySources) {
        propertySources.stream()
                .map(propertySource -> propertySource.getSource().keySet())
                .flatMap(Collection::stream)
                .distinct()
                .sorted()
                .forEach(key -> {
                    try {
                        log.info("{}={}", key, env.getProperty(key));
                    } catch (Exception e) {
                        log.warn("{} -> {}", key, e.getMessage());
                    }
                });
    }
}
