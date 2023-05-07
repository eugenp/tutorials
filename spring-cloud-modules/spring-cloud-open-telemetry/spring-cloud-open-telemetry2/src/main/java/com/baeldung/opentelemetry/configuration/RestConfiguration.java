package com.baeldung.opentelemetry.configuration;

import com.baeldung.opentelemetry.filter.RestTemplateHeaderModifierInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author : Nam Thang
 * @since : 04/05/2023, Thu
 **/
@Configuration
public class RestConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateHeaderModifierInterceptor()));
        return restTemplate;
    }
}
