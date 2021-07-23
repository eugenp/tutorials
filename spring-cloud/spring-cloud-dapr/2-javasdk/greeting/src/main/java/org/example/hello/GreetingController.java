package org.example.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GreetingController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getGreeting() {
        return "Welcome to the greeting service.";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getHello() {
        return "Hello world!";
    }

    @RequestMapping(value = "/goodbye", method = RequestMethod.GET)
    public String getGoodbye() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // Do nothing.
        }
        return "Goodbye, cruel world!";
    }
}
