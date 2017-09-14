package com.baeldung.constructordi.domain;

public class ResponseHandlers {

    private ResponseHandlerOne handlerOne;
    private ResponseHandlerTwo handlerTwo;

    public ResponseHandlers(ResponseHandlerOne one, ResponseHandlerTwo two){
        this.handlerOne=one;
        this.handlerTwo=two;
    }
}
