package com.baeldung.autowire.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceWithCI {

    private final Collaborator collaborator;

    @Autowired
    public ServiceWithCI(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public String useCollaborator() {
        return collaborator.doSomething();
    }

}
