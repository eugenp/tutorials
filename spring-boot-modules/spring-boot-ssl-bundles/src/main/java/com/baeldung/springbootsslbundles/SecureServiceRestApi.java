package com.baeldung.springbootsslbundles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SecureServiceRestApi {
    private final RestTemplate restTemplate;

    @Autowired
    public SecureServiceRestApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchData(String dataId) {
        ResponseEntity<String> response = restTemplate.exchange(
                "https://secure-service.com/api/data/{id}",
                HttpMethod.GET,
                null,
                String.class,
                dataId
        );
        return response.getBody();
    }
}
