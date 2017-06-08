package com.baeldung.ditypes.setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.ditypes.Guest;
import com.baeldung.ditypes.Host;

@Component
public class PodcastSetter {
    
    private String name = "Setter Injection";
    private Host host;
    private Guest guest;
    
    public void info(){
        System.out.println(String.format("Name: %s, %s, %s", this.name, this.host, this.guest));
    }

    @Autowired
    public void setHost(Host host) {
        this.host = host;
    }

    @Autowired
    public void setGuest(Guest guest) {
        this.guest = guest;
    }    
    
    // standard setters and getters
}
