package org.baeldung.webflux;

import org.baeldung.webflux.client.PulseWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.baeldung.webflux")
public class Application implements CommandLineRunner {

    @Autowired private PulseWebClient pulseClient;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        pulseClient.listenEvents();
    }
}
