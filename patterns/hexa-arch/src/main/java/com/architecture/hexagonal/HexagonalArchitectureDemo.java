package com.architecture.hexagonal;

import com.architecture.hexagonal.properties.ProductPathProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableConfigurationProperties({ProductPathProperties.class})
public class HexagonalArchitectureDemo {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalArchitectureDemo.class, args);
    }

}
