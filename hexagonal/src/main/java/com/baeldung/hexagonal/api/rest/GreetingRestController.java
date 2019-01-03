package com.baeldung.hexagonal.api.rest;

import com.baeldung.hexagonal.api.GreetingApi;
import com.baeldung.hexagonal.services.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("api-rest")
@RestController
@RequestMapping("/greeting")
public class GreetingRestController implements GreetingApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingRestController.class) ;

    private GreetingService greetingService ;

    @Autowired
    public GreetingRestController(GreetingService greetingService) {
        this.greetingService = greetingService ;
    }

    @Override
    @GetMapping
    public String sayHello(String nameFirst, String nameLast) {
        LOGGER.info("sayHello({}, {})", nameFirst, nameLast) ;
        return greetingService.createHelloGreeting(nameFirst, nameLast);
    }
}
