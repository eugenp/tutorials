package com.baeldung.dependencyTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DesktopComputer {
    private Case caze;
    private Keyboard keyboard;
    private Monitor monitor;
    private Mouse mouse;
    
    @Autowired
    public DesktopComputer(Case caze,Keyboard keyboard,Monitor monitor,Mouse mouse){
        this.caze = caze;
        this.keyboard = keyboard;
        this.monitor = monitor;
        this.mouse = mouse;
    }

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
