package com.baeldung.service;

import org.springframework.stereotype.Service;

@Service
public class SimpleService {

    public String getName(){
        return SimpleService.class.getName();
    }

}
