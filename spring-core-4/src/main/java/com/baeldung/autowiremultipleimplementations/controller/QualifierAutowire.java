package com.baeldung.autowiremultipleimplementations.controller;

import com.baeldung.autowiremultipleimplementations.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class QualifierAutowire {

    private final GoodService goodServiceA;
    private final GoodService goodServiceB;
    private final GoodService goodServiceC;

    @Autowired
    public QualifierAutowire(@Qualifier("goodServiceA-custom-name") GoodService goodServiceA,
                             @Qualifier("goodServiceB") GoodService goodServiceB,
                             GoodService goodServiceC) {
        this.goodServiceA = goodServiceA;
        this.goodServiceB = goodServiceB;
        this.goodServiceC = goodServiceC;
    }

    public String hello() {
        return goodServiceA.getHelloMessage() + " " +
                goodServiceB.getHelloMessage() + " " +
                goodServiceC.getHelloMessage();
    }
}
