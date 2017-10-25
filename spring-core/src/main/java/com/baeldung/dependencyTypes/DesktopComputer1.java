package com.baeldung.dependencyTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DesktopComputer1 {
    
    @Autowired
    private Case caze;
    @Autowired
    private Keyboard keyboard;
    @Autowired
    private Monitor monitor;
    @Autowired
    private Mouse mouse;
    public Case getCaze() {
        return caze;
    }
    public void setCaze(Case caze) {
        this.caze = caze;
    }
    public Keyboard getKeyboard() {
        return keyboard;
    }
    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }
    public Monitor getMonitor() {
        return monitor;
    }
    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }
    public Mouse getMouse() {
        return mouse;
    }
    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }
    
}
