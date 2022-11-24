package com.baeldung.sample;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints(JacksonRuntimeHints.PropertyNamingStrategyRegistrar.class)
public class JacksonRuntimeHints {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JacksonRuntimeHints.class);

    static class PropertyNamingStrategyRegistrar implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            try {
                hints
                  .reflection()
                  .registerField(PropertyNamingStrategies.class.getDeclaredField("SNAKE_CASE"));
                log.info("Registered native hint for SNAKE_CASE!");
            } catch (NoSuchFieldException e) {
                log.warn("Unable to register native hint for SNAKE_CASE!");
            }
        }
    }

}
