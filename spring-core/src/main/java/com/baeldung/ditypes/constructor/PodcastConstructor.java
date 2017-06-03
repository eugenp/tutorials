package com.baeldung.ditypes.constructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.ditypes.Guest;
import com.baeldung.ditypes.Host;

@Component
public class PodcastConstructor {
    
    private String name;
    private Host host;
    private Guest guest;
    
    @Autowired
    public PodcastConstructor(final Host host, final Guest guest){
        this.name = "Constructor Injection";
        this.host = host;
        this.guest = guest;
    }
    
    // standard setters and getters
    
    public void info(){
        System.out.println(String.format("Name: %s, %s, %s", this.name, this.host, this.guest));
    }
}
