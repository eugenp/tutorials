package com.baeldung.boot.jersey;

import com.baeldung.boot.jersey.controllers.HelloController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyResourceConfig extends ResourceConfig {

    public JerseyResourceConfig() {
        super(HelloController.class);
    }
}
