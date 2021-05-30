package com.baeldung.applicationcontextexception;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MainClass {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MainClass.class).web(WebApplicationType.NONE).run(args);
    }
}