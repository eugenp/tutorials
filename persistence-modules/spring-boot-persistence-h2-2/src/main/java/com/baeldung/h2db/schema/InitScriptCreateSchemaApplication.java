package com.baeldung.h2db.schema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InitScriptCreateSchemaApplication {

    public static void main(String... args) {
        SpringApplication.run(InitScriptCreateSchemaApplication.class, args);
    }

}