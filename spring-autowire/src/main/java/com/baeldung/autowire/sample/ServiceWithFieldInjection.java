package com.baeldung.autowire.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceWithFieldInjection {

    @Autowired
    private Collaborator collaborator;

    public String useCollaborator() {
        return collaborator.doSomething();
    }

}
