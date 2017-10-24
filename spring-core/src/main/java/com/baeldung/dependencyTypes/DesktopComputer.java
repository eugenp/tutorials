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
    
}
