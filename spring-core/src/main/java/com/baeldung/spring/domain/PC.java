package com.baeldung.spring.domain;

import org.springframework.beans.factory.annotation.Autowired;

public class PC {
    
    @Autowired
    private Cpu cpu;
    
    @Autowired
    private Monitor monitor;

    @Autowired
    private Keyboard keyboard;

    public PC(Cpu cpu, Monitor monitor, Keyboard keyboard) {
        this.cpu = cpu;
        this.monitor = monitor;
        this.keyboard = keyboard;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

}
