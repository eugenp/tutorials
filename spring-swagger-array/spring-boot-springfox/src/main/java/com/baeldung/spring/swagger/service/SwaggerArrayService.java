package com.baeldung.spring.swagger.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.baeldung.spring.swagger.model.SwaggerArray;
import com.baeldung.spring.swagger.repo.Repository;

@Component
public class SwaggerArrayService implements Service {

    @Autowired
    Repository swaggerArrayRepository;
    
    RestTemplate restTemplate = new RestTemplate();

    public List<SwaggerArray> getAll() {
        List<SwaggerArray> arrays = new ArrayList<SwaggerArray>();
        swaggerArrayRepository.findAll().forEach(array -> arrays.add(array));
        return arrays;
    }

    public void save(SwaggerArray array) {
        swaggerArrayRepository.save(array);
    }

}
