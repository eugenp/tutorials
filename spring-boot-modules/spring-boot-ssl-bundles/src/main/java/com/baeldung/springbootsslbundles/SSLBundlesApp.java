package com.baeldung.springbootsslbundles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.ssl.SslBundles;

@SpringBootApplication
public class SSLBundlesApp {
    public static void main(String[] args) {
        SpringApplication.run(SSLBundlesApp.class, args);
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, SslBundles sslBundles) {
        return restTemplateBuilder.setSslBundle(sslBundles.getBundle("secure-service")).build();
    }
}
