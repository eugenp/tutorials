package com.baeldung.resttemplate.web.service;

import com.baeldung.resttemplate.web.exception.UnauthorizedException;
import com.baeldung.resttemplate.web.handler.RestTemplateResponseErrorHandler;
import com.baeldung.resttemplate.web.model.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
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
        try {
            return restTemplate.getForObject("/bars/" + barId, Bar.class);
        } catch (HttpStatusCodeException e) {
            if (HttpStatus.UNAUTHORIZED == e.getStatusCode()) {
                String responseBody = e.getResponseBodyAsString();
                throw new UnauthorizedException("Unauthorized access: " + responseBody);
            }
            throw e;
        }
    }

}
