package org.example.hello;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GreetingController {
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

    @RequestMapping(value="/greeting", method=RequestMethod.POST)
    public void setGreeting(@RequestBody String greeting) throws Exception {
        System.out.println("Setting the greeting: " + greeting);
        // Simulate processing time
        Thread.sleep(10000);
        // We are returning normally, which is a 200, which means we processed event correctly.
        System.out.println("The greeting has been set.");
    }
}
