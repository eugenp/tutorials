package com.baeldung.spring.cloud.openservicebroker.config;

import org.springframework.cloud.servicebroker.model.catalog.Catalog;
import org.springframework.cloud.servicebroker.model.catalog.Plan;
import org.springframework.cloud.servicebroker.model.catalog.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogConfiguration {

    @Bean
    public Catalog catalog() {
        Plan mailFreePlan = Plan.builder()
            .id("fd81196c-a414-43e5-bd81-1dbb082a3c55")
            .name("mail-free-plan")
            .description("Mail Service Free Plan")
            .free(true)
            .build();

        ServiceDefinition serviceDefinition = ServiceDefinition.builder()
            .id("b92c0ca7-c162-4029-b567-0d92978c0a97")
            .name("mail-service")
            .description("Mail Service")
            .bindable(true)
            .tags("mail", "service")
            .plans(mailFreePlan)
            .build();

        return Catalog.builder()
            .serviceDefinitions(serviceDefinition)
            .build();
    }
}
