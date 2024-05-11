package com.baeldung.autowiremultipleimplementations.components;

import com.baeldung.autowiremultipleimplementations.candidates.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class QualifierAutowire {

    private final GoodService goodServiceA;
    private final GoodService goodServiceB;
    private final GoodService goodServiceC;

    @Autowired
    public QualifierAutowire(@Qualifier("goodServiceA-custom-name") GoodService niceServiceA,
                             @Qualifier("goodServiceB") GoodService niceServiceB,
                             GoodService goodServiceC) {
        this.goodServiceA = niceServiceA;
        this.goodServiceB = niceServiceB;
        this.goodServiceC = goodServiceC;
    }

    public String hello() {
        return goodServiceA.getHelloMessage() + " " +
                goodServiceB.getHelloMessage() + " " +
                goodServiceC.getHelloMessage();
    }
}
