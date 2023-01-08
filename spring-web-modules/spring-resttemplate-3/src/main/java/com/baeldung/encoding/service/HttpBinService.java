package com.baeldung.encoding.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class HttpBinService {
    private final RestTemplate restTemplate;

    public HttpBinService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String get(String parameter) throws JsonProcessingException {
        String url = "http://httpbin.org/get?parameter={parameter}";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, parameter);
        Map<String, Object> mapping = new ObjectMapper().readValue(response.getBody(), HashMap.class);
        Map<String, String> args = (Map<String, String>) mapping.get("args");
        return args.get("parameter");
    }
}
