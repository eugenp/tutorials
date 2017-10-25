package com.baeldung.beaninjectiontypes.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComputerConstructorInjection {
    private CPU cpu;
    private RAM ram;

    @Autowired
    public ComputerConstructorInjection(CPU cpu, RAM ram) {
        this.cpu = cpu;
        this.ram = ram;
    }

    @Override public String toString() {
        return String.format("Computer CPU %s RAM %s", cpu, ram);
    }
}