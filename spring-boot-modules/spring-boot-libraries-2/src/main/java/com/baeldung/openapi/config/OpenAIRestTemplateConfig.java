package com.baeldung.openapi.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for RestTemplate used to communicate with OpenAI API.
 */
@Configuration
public class OpenAIRestTemplateConfig {
    /**
     * API key for OpenAI API authentication.
     */
    @Value("${openai.api.key}")
    private String openaiApiKey;

    /**
     * Configures RestTemplate with OpenAI API key in header for authentication.
     *
     * @return RestTemplate instance with OpenAI API key header
     */
    @Bean
    @Qualifier("openaiRestTemplate")
    public RestTemplate openaiRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}