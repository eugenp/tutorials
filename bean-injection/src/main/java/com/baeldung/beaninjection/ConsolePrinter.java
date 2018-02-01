package com.baeldung.beaninjection;

import org.springframework.stereotype.Service;

@Service
public class ConsolePrinter implements IPrinter {

    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
