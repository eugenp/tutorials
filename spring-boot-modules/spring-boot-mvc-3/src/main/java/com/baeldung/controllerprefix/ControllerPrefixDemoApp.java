package com.baeldung.controllerprefix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class ControllerPrefixDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(ControllerPrefixDemoApp.class, args);
    }
}

@Controller
class EndpointController {
    @GetMapping("/endpoint1")
    @ResponseBody
    public String endpoint1() {
        return "Hello from endpoint 1";
    }

    @GetMapping("/endpoint2")
    @ResponseBody
    public String endpoint2() {
        return "Hello from endpoint 2";
    }
}
