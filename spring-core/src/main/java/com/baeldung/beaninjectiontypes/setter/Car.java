package com.baeldung.beaninjectiontypes.setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {

    private Type type;

    public Type getType() {
        return type;
    }

    @Autowired
    public void setType(Type type) {
        this.type = type;
    }
}
