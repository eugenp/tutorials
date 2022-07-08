package com.baeldung.springcloudgateway.custompredicates;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CustomPredicatesApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CustomPredicatesApplication.class)
          .profiles("customroutes")
          .run(args);
    }

}