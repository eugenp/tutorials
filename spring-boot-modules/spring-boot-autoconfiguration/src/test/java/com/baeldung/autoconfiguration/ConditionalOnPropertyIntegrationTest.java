package com.baeldung.autoconfiguration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

import com.baeldung.autoconfiguration.service.CustomService;
import com.baeldung.autoconfiguration.service.DefaultService;
import com.baeldung.autoconfiguration.service.SimpleService;

public class ConditionalOnPropertyIntegrationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

    @Test
    public void whenGivenCustomPropertyValue_thenCustomServiceCreated() {
        this.contextRunner.withPropertyValues("com.baeldung.service=custom")
            .withUserConfiguration(SimpleServiceConfiguration.class)
            .run(context -> {
                assertThat(context).hasBean("customService");
                SimpleService simpleService = context.getBean(CustomService.class);
                assertThat(simpleService.serve()).isEqualTo("Custom Service");
                assertThat(context).doesNotHaveBean("defaultService");
            });
    }

    @Test
    public void whenGivenDefaultPropertyValue_thenDefaultServiceCreated() {
        this.contextRunner.withPropertyValues("com.baeldung.service=default")
            .withUserConfiguration(SimpleServiceConfiguration.class)
            .run(context -> {
                assertThat(context).hasBean("defaultService");
                SimpleService simpleService = context.getBean(DefaultService.class);
                assertThat(simpleService.serve()).isEqualTo("Default Service");
                assertThat(context).doesNotHaveBean("customService");
            });
    }

    @Configuration
    @TestPropertySource("classpath:ConditionalOnPropertyTest.properties")
    protected static class SimpleServiceConfiguration {

        @Bean
        @ConditionalOnProperty(name = "com.baeldung.service", havingValue = "default")
        @ConditionalOnMissingBean
        public DefaultService defaultService() {
            return new DefaultService();
        }

        @Bean
        @ConditionalOnProperty(name = "com.baeldung.service", havingValue = "custom")
        @ConditionalOnMissingBean
        public CustomService customService() {
            return new CustomService();
        }
    }

}
