package com.baeldung.sample;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@ImportRuntimeHints(GraphQlRuntimeHints.GraphQlResourcesRegistrar.class)
@Configuration
public class GraphQlRuntimeHints {

    static class GraphQlResourcesRegistrar implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.resources()
              .registerPattern("graphql/**/")
              .registerPattern("graphiql/index.html");
        }
    }

}
