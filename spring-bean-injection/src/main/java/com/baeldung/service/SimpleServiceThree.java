package com.baeldung.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleServiceThree {
    private SimpleService simpleService;

    @Autowired
    public void setSimpleService(SimpleService simpleService){
        this.simpleService = simpleService;
    }

    public String getNameViaSimpleService(){
        return this.simpleService.getName();
    }
}
