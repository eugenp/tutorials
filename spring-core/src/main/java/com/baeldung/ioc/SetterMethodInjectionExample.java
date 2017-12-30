package com.baeldung.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetterMethodInjectionExample {
    private A a;
    private B b;

    public void sayHello() {
        if (a.isAlive() && (b.isAlive()))
            System.out.println(
                "Hello, I'm SetterMethodInjectionExample and my variables are alive");
    }

    public A getA() {
        return a;
    }

    @Autowired
    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    @Autowired
    public void setB(B b) {
        this.b = b;
    }
}
