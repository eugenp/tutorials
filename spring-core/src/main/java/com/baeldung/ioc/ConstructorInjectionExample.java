package com.baeldung.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjectionExample {
    private A a;

    private B b;

    @Autowired
    public ConstructorInjectionExample(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public ConstructorInjectionExample() {
    }

    public void sayHello() {
        if (a.isAlive() && b.isAlive())
            System.out.println(
                "Hello, I'm ConstructorInjectionExample and my variables are alive");
    }
}
