package com.baeldung.dependencyTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DesktopComputer2 {
    
    private Case caze;
    private Keyboard keyboard;
    private Monitor monitor;
    private Mouse mouse;
    
    @Autowired
    public void setCaze(Case caze) {
        this.caze = caze;
    }

    @Autowired
    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    @Autowired
    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    @Autowired
    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

}
