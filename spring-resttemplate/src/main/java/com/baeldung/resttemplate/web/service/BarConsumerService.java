package com.baeldung.resttemplate.web.service;

import com.baeldung.resttemplate.web.handler.RestTemplateResponseErrorHandler;
import com.baeldung.resttemplate.web.model.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BarConsumerService {

    private RestTemplate restTemplate;

    @Autowired
    public BarConsumerService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
          .errorHandler(new RestTemplateResponseErrorHandler())
          .build();
    }

    public Bar fetchBarById(String barId) {
        return restTemplate.getForObject("/bars/4242", Bar.class);
    }

}
