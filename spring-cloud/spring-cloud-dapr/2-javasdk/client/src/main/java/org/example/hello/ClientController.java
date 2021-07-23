package org.example.hello;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.HttpExtension;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ClientController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getHello() throws Exception {
        System.out.println("Getting the hello greeting...");
        try (DaprClient client = (new DaprClientBuilder()).build()) {
            byte[] response = client.invokeMethod("greeting", "hello", "hello", HttpExtension.GET, null,
                byte[].class).block();
            return new String(response);
        }
    }

    @RequestMapping(value = "/goodbye", method = RequestMethod.GET)
    public String getGoodbye() throws Exception {
        System.out.println("Getting the goodbye greeting...");
        try (DaprClient client = (new DaprClientBuilder()).build()) {
            byte[] response = client.invokeMethod("greeting", "goodbye", "goodbye", HttpExtension.GET, null,
                byte[].class).block();
            return new String(response);
        }
    }

    @RequestMapping(value = "/greeting/{greeting}", method = RequestMethod.POST)
    public void setGreeting(@PathVariable("greeting") String greeting) throws Exception {
        System.out.println("Setting the greeting...");
        try (DaprClient client = (new DaprClientBuilder()).build()) {
            client.invokeBinding("greeting", "create", greeting).block();
        }
    }
}
