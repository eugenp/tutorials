package com.baeldung.impl;

import com.baeldung.service.GreetingPort;

public class GreetingPortStaticAdapter implements GreetingPort {

    @Override
    public String getGreetingPhrase() {
        return "Hello";
    }

    @Override
    public String getDesiredLanguage() {
        return "es"; //"en", "de", ...
    }
}
