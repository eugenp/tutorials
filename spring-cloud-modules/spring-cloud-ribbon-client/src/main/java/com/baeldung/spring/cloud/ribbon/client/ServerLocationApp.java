package com.baeldung.spring.cloud.ribbon.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@RibbonClient(name = "ping-a-server", configuration = RibbonConfiguration.class)
public class ServerLocationApp {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/server-location")
    public String serverLocation() {
        return this.restTemplate.getForObject("http://ping-server/locaus", String.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerLocationApp.class, args);
    }
}
