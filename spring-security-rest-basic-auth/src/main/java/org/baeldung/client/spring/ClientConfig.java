package org.baeldung.client.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.client")
public class ClientConfig {

    public ClientConfig() {
        super();
    }

    // beans

}