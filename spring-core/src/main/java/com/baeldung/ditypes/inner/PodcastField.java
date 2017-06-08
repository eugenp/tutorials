package com.baeldung.ditypes.inner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.ditypes.Guest;
import com.baeldung.ditypes.Host;

@Component
public class PodcastField {
    
    private String name = "Field Injection";
    
    @Autowired
    private Host host;
    
    @Autowired
    private Guest guest;
    
    public void info(){
        System.out.println(String.format("Name: %s, %s, %s", this.name, this.host, this.guest));
    }
    
    // standard setters and getters
}
