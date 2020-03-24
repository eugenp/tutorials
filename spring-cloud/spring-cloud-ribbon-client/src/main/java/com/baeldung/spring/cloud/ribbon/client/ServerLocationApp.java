package com.baeldung.spring.cloud.ribbon.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@RibbonClient(name = "ping-a-server", configuration = RibbonConfiguration.class)
public class ServerLocationApp {

    @LoadBalanced
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/server-location")
    public String serverLocation() {
        String servLoc = this.restTemplate.getForObject("http://ping-server/locaus", String.class);
        return servLoc;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerLocationApp.class, args);
    }
}
