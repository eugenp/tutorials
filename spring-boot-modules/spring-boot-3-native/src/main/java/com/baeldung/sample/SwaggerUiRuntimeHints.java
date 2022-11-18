package com.baeldung.sample;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@ImportRuntimeHints(SwaggerUiRuntimeHints.SwaggerUiResourcesRegistrar.class)
@Configuration
public class SwaggerUiRuntimeHints {

    static class SwaggerUiResourcesRegistrar implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.resources()
              .registerPattern("META-INF/resources/webjars/**");
        }
    }

}
