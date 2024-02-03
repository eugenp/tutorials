package com.baeldung.failureanalyzer;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class MyService {

    @Resource(name = "myDAO")
    private MyDAO myDAO;

}
