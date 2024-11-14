package com.baeldung.disableautowiring.thirdpartylib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestBean {

    @Autowired
    private TestDependency testDependency;
}
