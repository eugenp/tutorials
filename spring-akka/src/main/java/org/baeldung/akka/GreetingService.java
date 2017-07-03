package org.baeldung.akka;

import org.springframework.stereotype.Component;

@Component
class GreetingService {

    public String greet(String name) {
        return "Hello, " + name;
    }

}
