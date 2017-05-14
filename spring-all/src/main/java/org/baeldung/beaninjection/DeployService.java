package org.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeployService {

    private Notification notification;
    
    @Autowired
    public DeployService(Notification notification) {
        this.notification = notification;
    }
    
    public void deploy() {
        notification.send("The deploy is about to start.");
    }
}
