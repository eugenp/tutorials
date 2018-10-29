package com.baeldung.springejbclient.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.ejb.tutorial.HelloStatefulWorld;
import com.baeldung.ejb.tutorial.HelloStatelessWorld;

@RestController
public class HomeEndpoint {
    
    private HelloStatelessWorld helloStatelessWorld;
    private HelloStatefulWorld helloStatefulWorld;

    public HomeEndpoint(HelloStatelessWorld helloStatelessWorld, HelloStatefulWorld helloStatefulWorld) {
        this.helloStatelessWorld = helloStatelessWorld;
        this.helloStatefulWorld = helloStatefulWorld;
    }

    @GetMapping("/stateless")
    public String getStateless() {
        return helloStatelessWorld.getHelloWorld();
    }
    
    @GetMapping("/stateful")
    public String getStateful() {
        return helloStatefulWorld.getHelloWorld() + " called " + helloStatefulWorld.howManyTimes() + " times";
    }

}
