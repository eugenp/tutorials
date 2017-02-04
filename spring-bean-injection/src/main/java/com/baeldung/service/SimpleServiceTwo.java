package com.baeldung.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleServiceTwo {

    private final SimpleService simpleService;

    @Autowired
    public SimpleServiceTwo(SimpleService simpleService){
        this.simpleService = simpleService;
    }

    public String getNameViaSimpleService(){
        return this.simpleService.getName();
    }
}
