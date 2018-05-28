package com.baeldung.webfluxdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainApp.class, args);

        SayHelloWebClient gwc = new SayHelloWebClient();
        while (true) {
            System.out.println(gwc.getResult());
            Thread.sleep(1000);
        }
    }
}
