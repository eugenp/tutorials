package com.baeldung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Client {
    Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {

        SpringApplication.run(Client.class);
        DataConsumer recieveController = new DataConsumer();
        recieveController.getData();
    }
}
