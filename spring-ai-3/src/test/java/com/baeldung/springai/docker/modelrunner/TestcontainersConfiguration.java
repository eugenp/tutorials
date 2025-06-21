package com.baeldung.springai.docker.modelrunner;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.DockerModelRunnerContainer;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    DockerModelRunnerContainer socat() {
        return new DockerModelRunnerContainer("alpine/socat:1.8.0.1");
    }

    @Bean
    DynamicPropertyRegistrar properties(DockerModelRunnerContainer dmr) {
        return (registrar) -> {
          registrar.add("spring.ai.openai.base-url", dmr::getOpenAIEndpoint);
          registrar.add("spring.ai.openai.api-key", () -> "test-api-key");
          registrar.add("spring.ai.openai.chat.options.model", () -> "ai/gemma3");
        };
    }
}
