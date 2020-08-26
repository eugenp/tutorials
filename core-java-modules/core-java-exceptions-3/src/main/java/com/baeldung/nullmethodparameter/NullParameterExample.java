package com.baeldung.nullmethodparameter;

public class NullParameterExample {
    public void processSomethingNotNull(Object myParameter) {
        if (myParameter == null) {
            throw new IllegalArgumentException("Parameter 'myParameter' cannot be null");
        }
        //Do something with the parameter
    }
    
    public void processSomethingElseNotNull(Object myParameter) {
        if (myParameter == null) {
            throw new NullPointerException("Parameter 'myParameter' cannot be null");
        }
        //Do something with the parameter
    }
}
