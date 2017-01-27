package com.baeldung.inject.annotation.setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Oleg Cherednik
 * @since 27.01.2017
 */
@Service
public class SomeService {
    @Autowired
    @Qualifier("a")
    private AnotherService a;
    @Autowired
    @Qualifier("b")
    private AnotherService b;

    public AnotherService getA() {
        return a;
    }

    public AnotherService getB() {
        return b;
    }
}
