package com.baeldung.micronaut.environments;

import io.micronaut.context.env.Environment;
import io.micronaut.runtime.Micronaut;

public class ServerApplication {

    public static void main(String[] args) {
        Micronaut.build(args)
            .defaultEnvironments(Environment.DEVELOPMENT)
            .mainClass(ServerApplication.class)
            .start();
    }
}
