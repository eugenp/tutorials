package com.baeldung.swaggeryml;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SwaggerYMLApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SwaggerYMLApplication.class)
          .properties("spring.config.name=application-yml")
          .run(args);
    }
}