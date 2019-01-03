package com.baeldung.hexagonal.api.jmx;

import com.baeldung.hexagonal.api.GreetingApi;
import com.baeldung.hexagonal.services.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Profile("api-jmx")
@Component
@ManagedResource
public class GreetingJmxResource implements GreetingApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingJmxResource.class) ;

    private GreetingService greetingService;

    @Autowired
    public GreetingJmxResource(GreetingService greetingService) {
        this.greetingService = greetingService ;
    }

    @Override
    @ManagedOperation
    public String sayHello(String nameFirst, String nameLast) {
        LOGGER.info("sayHello({}, {})", nameFirst, nameLast) ;
        return greetingService.createHelloGreeting(nameFirst, nameLast);
    }
}
