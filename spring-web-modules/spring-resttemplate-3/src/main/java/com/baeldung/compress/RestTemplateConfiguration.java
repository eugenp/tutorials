package com.baeldung.compress;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    @Primary
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Remove XML converters to ensure JSON is used
        restTemplate.setMessageConverters(
          restTemplate.getMessageConverters().stream()
            .filter(converter -> !(converter instanceof MappingJackson2XmlHttpMessageConverter))
            .collect(Collectors.toList())
        );

        restTemplate.getInterceptors().add(new CompressingClientHttpRequestInterceptor());
        return restTemplate;
    }
}
