package com.baeldung.contexts.services;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baeldung.contexts.Greeting;

@Service
public class GreeterService {
    
    @Resource
    private Greeting greeting;
    
    public String greet(){
        return greeting.getMessage();
    }
    
}
