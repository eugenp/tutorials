package com.baeldung.autowire.sample;

import org.springframework.stereotype.Component;

@Component
class CalcServiceImpl implements CalcService {
    @Override
    public int doubled(int x) {
       return x * 2;
    }
}

