package com.baeldung.beaninjectionautowired;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class SimpleBean {

    public String ping() {
        return this + " is alive";
    }
}
