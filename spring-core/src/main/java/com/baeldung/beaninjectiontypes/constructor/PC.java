package com.baeldung.beaninjectiontypes.constructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PC {

    private CPU cpu;

    @Autowired
    public PC(CPU cpu) {
        this.cpu = cpu;
    }

    public CPU getCpu() {
        return cpu;
    }
}
