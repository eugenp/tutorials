package com.baeldung.spring.mvc;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.AbstractClientHttpServiceRegistrar;

@Configuration
@Import(HttpClientConfig.HelloWorldClientHttpServiceRegistrar.class)
public class HttpClientConfig {

    static class HelloWorldClientHttpServiceRegistrar extends AbstractClientHttpServiceRegistrar {

        @Override
        protected void registerHttpServices(@NonNull GroupRegistry registry, @NonNull AnnotationMetadata metadata) {
            findAndRegisterHttpServiceClients(registry, List.of("com.baeldung.spring.mvc"));
        }
    }

    @Bean
    RestClientHttpServiceGroupConfigurer christmasJoyServiceGroupConfigurer(@Value("${application.rest.services.christmasJoy.baseUrl}") String baseUrl) {
        return groups -> {
            groups.filterByName("christmasJoy")
                .forEachClient((group, clientBuilder) -> {
                    clientBuilder.baseUrl(baseUrl);
                });
        };
    }

}
