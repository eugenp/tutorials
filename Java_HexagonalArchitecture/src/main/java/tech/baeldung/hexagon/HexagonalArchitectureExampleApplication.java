package tech.baeldung.hexagon;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration
public class HexagonalArchitectureExampleApplication {
	//starting point for application
    public static void main(String[] args) {
        SpringApplication.run(HexagonalArchitectureExampleApplication.class, args);
    }

}
