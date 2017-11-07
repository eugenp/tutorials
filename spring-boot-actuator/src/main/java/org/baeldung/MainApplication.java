package org.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.baeldung.config.MainConfig;

@SpringBootApplication
public class MainApplication {

    public static void main(String args[]) {
        SpringApplication.run(MainConfig.class, args);
    }
}
