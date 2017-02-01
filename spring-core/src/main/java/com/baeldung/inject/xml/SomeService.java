package com.baeldung.inject.xml;

/**
 * @author Oleg Cherednik
 * @since 27.01.2017
 */
public class SomeService {
    private AnotherService a;
    private AnotherService b;

    public SomeService() {
    }

    public SomeService(AnotherService a) {
        this.a = a;
    }

    public SomeService(AnotherService a, AnotherService b) {
        this.a = a;
        this.b = b;
    }

    public AnotherService getA() {
        return a;
    }

    public void setA(AnotherService a) {
        this.a = a;
    }

    public AnotherService getB() {
        return b;
    }

    public void setB(AnotherService b) {
        this.b = b;
    }
}
