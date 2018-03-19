package com.baeldung.beaninjectiontypes.demo;

import org.springframework.stereotype.Component;

@Component
public class BasicEncrypter implements Encrypter{

    public String encrypt(String input) {
        return input;
    }
}
