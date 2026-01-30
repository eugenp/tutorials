package com.baeldung.spring.httpinterface;

public class MyServiceException extends RuntimeException {

    MyServiceException(String msg) {
        super(msg);
    }

}