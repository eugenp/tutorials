package com.baeldung.aspectj.classmethodadvice;

import org.springframework.stereotype.Component;

@Component
public class MyTracedServiceConsumer {

    public MyTracedServiceConsumer(MyTracedService myTracedService) {
        myTracedService.performSomeLogic();
        myTracedService.performSomeAdditionalLogic();
    }
}
