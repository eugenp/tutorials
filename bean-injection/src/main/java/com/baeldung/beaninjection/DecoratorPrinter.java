package com.baeldung.beaninjection;

import org.springframework.stereotype.Service;

@Service
public class DecoratorPrinter implements IPrinter {

    @Override
    public void print(String message) {
        System.out.println("================================");
        System.out.println("********************************");
        System.out.println("================================");
        System.out.println(message);
        System.out.println("================================");
    }
}
