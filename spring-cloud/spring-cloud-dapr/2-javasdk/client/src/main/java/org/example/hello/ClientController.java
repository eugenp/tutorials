package org.example.hello;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.HttpExtension;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ClientController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getGreeting() {
        return "Welcome to the greeting service.";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getHello() {
        try (DaprClient client = (new DaprClientBuilder()).build()) {
            byte[] response = client.invokeMethod("greeting", "hello", "hello", HttpExtension.GET, null,
                byte[].class).block();
            return new String(response);
        } catch (Exception e) {
            System.out.println("Exception calling greeting service: " + e.getMessage());
            throw new RuntimeException("Exception calling greeting service: ", e);
        }
    }

    @RequestMapping(value = "/goodbye", method = RequestMethod.GET)
    public String getGoodbye() {
        try (DaprClient client = (new DaprClientBuilder()).build()) {
            byte[] response = client.invokeMethod("greeting", "goodbye", "goodbye", HttpExtension.GET, null,
                byte[].class).block();
            return new String(response);
        } catch (Exception e) {
            System.out.println("Exception calling greeting service: " + e.getMessage());
            throw new RuntimeException("Exception calling greeting service: ", e);
        }
    }
}
