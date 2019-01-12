package com.baeldung.hexagonal.api.rest;

import com.baeldung.hexagonal.api.GreetingApi;
import com.baeldung.hexagonal.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/greeting")
public class GreetingRestController implements GreetingApi {

    private GreetingService greetingService;

    @Autowired
    public GreetingRestController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @Override
    @GetMapping
    public String sayHello(String nameFirst, String nameLast) {
        return greetingService.createHelloGreeting(nameFirst, nameLast);
    }

    @Override
    @GetMapping("/list")
    public List<String> listAll() {
        return greetingService.listAll();
    }
}
