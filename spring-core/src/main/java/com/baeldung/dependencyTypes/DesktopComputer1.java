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

}
