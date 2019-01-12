package com.baeldung.hexagonal.api.jmx;

import com.baeldung.hexagonal.api.GreetingApi;
import com.baeldung.hexagonal.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ManagedResource
public class GreetingJmxResource implements GreetingApi {

    private GreetingService greetingService;

    @Autowired
    public GreetingJmxResource(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @Override
    @ManagedOperation
    public String sayHello(String nameFirst, String nameLast) {
        return greetingService.createHelloGreeting(nameFirst, nameLast);
    }

    @Override
    @ManagedOperation
    public List<String> listAll() {
        return greetingService.listAll();
    }
}
