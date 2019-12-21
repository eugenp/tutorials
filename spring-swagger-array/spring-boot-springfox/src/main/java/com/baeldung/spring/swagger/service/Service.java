package com.baeldung.spring.swagger.service;

import java.util.List;

import com.baeldung.spring.swagger.model.SwaggerArray;

public interface Service {
    public List<SwaggerArray> getAll();

    public void save(SwaggerArray hero);

}
