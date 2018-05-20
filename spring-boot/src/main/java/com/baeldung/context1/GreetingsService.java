package com.baeldung.context1;

import org.springframework.stereotype.Service;
import com.baeldung.contexts.IHomeService;

@Service
public class GreetingsService implements IHomeService {

    public String getGreeting() {
        return "Greetings for the day";
    }
}