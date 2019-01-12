package com.baeldung.hexagonal.services;

import com.baeldung.hexagonal.model.Greeting;
import com.baeldung.hexagonal.model.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GreetingService {

    @Autowired
    private GreetingRepository greetingRepository;

    public String createHelloGreeting(String nameFirst, String nameLast) {
        String message = String.format("Hello, %s %s!  How are you today?", nameFirst, nameLast);
        greetingRepository.save(new Greeting(message));
        return message;
    }

    public List<String> listAll() {
        return greetingRepository.findAll().stream().map(greeting -> greeting.getText()).collect(Collectors.toList());
    }
}
