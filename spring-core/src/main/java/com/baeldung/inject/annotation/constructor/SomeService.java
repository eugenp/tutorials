package com.baeldung.inject.annotation.constructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Oleg Cherednik
 * @since 27.01.2017
 */
@Service
public class SomeService {
    private AnotherService a;
    private AnotherService b;

    public SomeService(@Qualifier("a") AnotherService a, @Qualifier("b") AnotherService b) {
        this.a = a;
        this.b = b;
    }

    public AnotherService getA() {
        return a;
    }

    public AnotherService getB() {
        return b;
    }
}
