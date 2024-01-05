package com.baeldung.restart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.stereotype.Service;

@Service
public class RestartService {
    
    @Autowired
    private RestartEndpoint restartEndpoint;
    
    public void restartApp() {
        restartEndpoint.restart();
    }

}