package com.baeldung.autowire.sample;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class Calculator {
    private CalcService service;

    @Autowired
    public Calculator(CalcService service) {
        this.service = service;
    }

    //@Autowired
    //public void setService(CalcService service) {
    //    this.service = service;
    //}

    public int calculate(int x) {
        return service.doubled(x);
    }
}

