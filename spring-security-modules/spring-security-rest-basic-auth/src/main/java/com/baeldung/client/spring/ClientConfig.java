package com.baeldung.client.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.client")
public class ClientConfig {

    public ClientConfig() {
        super();
    }

    // beans

}