package com.baeldung.setterdi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandlersWithAnnotations {

    private ResponseHandlerOne handlerOne;
    private ResponseHandlerTwo handlerTwo;

    @Autowired
    public void setResponseHandlerOne(ResponseHandlerOne handlerOne) {
        this.handlerOne = handlerOne;
    }

    @Autowired
    public void setResponseHandlerTwo(ResponseHandlerTwo handlerTwo) {
        this.handlerTwo = handlerTwo;
    }
}
