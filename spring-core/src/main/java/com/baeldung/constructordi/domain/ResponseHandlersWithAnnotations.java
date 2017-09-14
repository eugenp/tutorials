package com.baeldung.constructordi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandlersWithAnnotations {

    private ResponseHandlerOne handlerOne;
    private ResponseHandlerTwo handlerTwo;

    @Autowired
    public ResponseHandlersWithAnnotations(ResponseHandlerOne one, ResponseHandlerTwo two){
        this.handlerOne=one;
        this.handlerTwo=two;
    }
}
