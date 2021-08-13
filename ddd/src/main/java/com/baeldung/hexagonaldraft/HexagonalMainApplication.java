package com.baeldung.hexagonaldraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "com.baeldung.hexagonaldraft")
@PropertySource(value = { "classpath:hexagonal-draft.properties" })
public class HexagonalMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalMainApplication.class, args);
    }

}
