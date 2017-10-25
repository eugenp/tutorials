package com.baeldung.beaninjectiontypes.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComputerSetterInjection {
    private CPU cpu;
    private RAM ram;

    @Autowired
    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    @Autowired
    public void setRam(RAM ram) {
        this.ram = ram;
    }

    @Override public String toString() {
        return String.format("Computer CPU %s RAM %s", cpu, ram);
    }
}
