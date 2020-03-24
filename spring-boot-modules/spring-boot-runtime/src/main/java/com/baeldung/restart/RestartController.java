package com.baeldung.restart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestartController {
    
    @Autowired
    private RestartService restartService;
    
    @PostMapping("/restart")
    public void restart() {
        Application.restart();
    }
    
    @PostMapping("/restartApp")
    public void restartUsingActuator() {
        restartService.restartApp();
    }
    
}
