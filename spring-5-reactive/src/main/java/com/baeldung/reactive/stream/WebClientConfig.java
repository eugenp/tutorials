/**
 *
 */
package com.baeldung.reactive.stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author goobar
 *
 */
@Configuration
public class WebClientConfig {
    /**
     * @return the configured {@link WebClient}
     */
    @Bean
    public WebClient webClient() {
        return WebClient.create("http://localhost:8080");
    }
}
