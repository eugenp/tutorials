package org.baeldung.testmethodsecurity.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class SystemPropImpl implements SystemPropInterface{

    @PreAuthorize("permitAll")
    @Override
    public String getSystemName() {
        return "Method Security";
    }
    
    @Override
    public String sayHello(){
        return sayHi();
    }
    
    @Secured("ROLE_USER")
    public String sayHi(){
        return "Hi";
    }

}
