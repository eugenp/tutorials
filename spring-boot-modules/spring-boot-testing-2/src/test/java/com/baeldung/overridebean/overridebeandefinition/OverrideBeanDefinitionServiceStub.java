package com.baeldung.overridebean.overridebeandefinition;

import com.baeldung.overridebean.Service;

public class OverrideBeanDefinitionServiceStub implements Service {

    public String helloWorld() {
        return "hello no profile stub";
    }
}
