package org.baeldung.examples.olingo2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Olingo2SampleApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new Olingo2SampleApplication()
          .configure(new SpringApplicationBuilder(Olingo2SampleApplication.class))
          .run(args);
    }
}
