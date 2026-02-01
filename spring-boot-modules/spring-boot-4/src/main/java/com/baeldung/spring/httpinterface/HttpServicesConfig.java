package com.baeldung.spring.httpinterface;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.support.WebClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(group = "books", types = { BooksService.class, AuthorsService.class })
@ImportHttpServices(group = "payments", types = PaymentService.class)
class HttpServicesConfig {

    @Bean
    WebClientHttpServiceGroupConfigurer groupConfigurer() {
        return groups -> {
            groups.forEachClient((group, builder) -> builder
                .defaultHeader("User-Agent", "Baeldung-Client v1.0"));

            groups.filterByName("books")
                .forEachClient((group, builder) -> builder
                    .defaultUriVariables(Map.of("foo", "bar"))
                    .defaultApiVersion("v1"));
        };
    }

}