package org.baeldung.web.service;

import org.baeldung.web.handler.RestTemplateResponseErrorHandler;
import org.baeldung.web.model.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class BarConsumerService {


    @Autowired RestTemplateBuilder restTemplateBuilder;

    public Bar fetchBarById(String barId) {
        RestTemplate restTemplate = restTemplateBuilder
          .errorHandler(new RestTemplateResponseErrorHandler())
          .build();
        return restTemplate.getForObject("/bars/4242", Bar.class);
    }

}
