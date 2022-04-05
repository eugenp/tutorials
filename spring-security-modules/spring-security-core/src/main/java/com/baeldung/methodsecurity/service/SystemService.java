package com.baeldung.methodsecurity.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class SystemService {
    
    public String getSystemYear(){
        return "2017";
    }
    
    public String getSystemDate(){
        return "31-12-2017";
    }
    
}
