package com.baeldung.jfrevent;

import org.springframework.stereotype.Component;

@Component
public class LegacyClass {
    @Deprecated(forRemoval = true)
    public void oldMethod() {
        System.out.println("Deprecated method");
    }
    
    public void callDeprecatedMethod() {
        Boolean boolean1 = new Boolean("true");
    }
    
    public void wrapperCall() {
        //Warm up phase to allow JIT to identify and compile 'hot' methods
        for (int i = 0; i < 26000; i++) {
            callDeprecatedMethod();
        }
        callDeprecatedMethod();
        Boolean boolean2 = new Boolean("false");
    }
}
