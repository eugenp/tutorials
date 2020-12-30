package com.baeldung.compress;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    /**
     * A RestTemplate that compresses requests.
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new CompressingClientHttpRequestInterceptor());
        return restTemplate;
    }
}
