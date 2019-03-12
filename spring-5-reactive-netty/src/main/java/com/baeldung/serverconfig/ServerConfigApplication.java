package com.baeldung.serverconfig;

import com.baeldung.serverconfig.client.GreetingWebClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerConfigApplication.class, args);

        GreetingWebClient webClient = new GreetingWebClient();
        webClient.getResult();
    }
}
