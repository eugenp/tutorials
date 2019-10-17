package com.baeldung.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ExternalPropertyFileLoader {

    @Autowired
    ConfProperties prop;

    public static void main(String[] args) {
        new SpringApplicationBuilder(ExternalPropertyFileLoader.class).build()
        .run(args);
    }

}
