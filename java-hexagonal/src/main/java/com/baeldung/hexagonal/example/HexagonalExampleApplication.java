package com.baeldung.hexagonal.example;

import com.baeldung.hexagonal.example.configuration.DomainUseCaseConfiguration;
import com.baeldung.hexagonal.example.configuration.InputAdapterConfiguration;
import com.baeldung.hexagonal.example.configuration.OutputAdapterConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@Import({
        OutputAdapterConfiguration.class,
        DomainUseCaseConfiguration.class,
        InputAdapterConfiguration.class
})
@EnableAutoConfiguration
@SpringBootConfiguration
public class HexagonalExampleApplication extends SpringBootServletInitializer {
    public static void main(String... args) {
        SpringApplication.run(HexagonalExampleApplication.class, args);
    }
}
