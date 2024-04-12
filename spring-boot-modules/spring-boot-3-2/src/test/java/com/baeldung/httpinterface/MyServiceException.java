package com.baeldung.httpinterface;

public class MyServiceException extends RuntimeException {

    MyServiceException(String msg) {
        super(msg);
    }

}
