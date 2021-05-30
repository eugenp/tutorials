package com.baeldung.applicationcontextexception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Remove this annotation to produce ApplicationContextException
@SpringBootApplication
public class MainEntryPoint {

    public static void main(String[] args) {
        SpringApplication.run(MainEntryPoint.class, args);
    }

}
