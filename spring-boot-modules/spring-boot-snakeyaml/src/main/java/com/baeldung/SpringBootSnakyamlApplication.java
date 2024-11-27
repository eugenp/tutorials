package com.baeldung;

import com.baeldung.boot.SafeYamlLoader;
import com.baeldung.boot.VulnerableYamlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootSnakyamlApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootSnakyamlApplication.class);

    private final VulnerableYamlLoader vulnerableYamlLoader;
    private final SafeYamlLoader safeYamlLoader;

    public SpringBootSnakyamlApplication(VulnerableYamlLoader vulnerableYamlLoader, SafeYamlLoader safeYamlLoader) {
        this.vulnerableYamlLoader = vulnerableYamlLoader;
        this.safeYamlLoader = safeYamlLoader;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSnakyamlApplication.class, args);
    }

    @Override
    public void run(String... args) {
        String yamlContent = "key: value\nanotherKey: !!java.util.Date {}";

        logger.info("Running Vulnerable Loader:");
        Object vulnerableResult = vulnerableYamlLoader.loadYaml(yamlContent);
        logger.info("Vulnerable Result: {}", vulnerableResult);

        logger.info("Running Safe Loader:");
        Object safeResult = safeYamlLoader.loadYamlSafely(yamlContent);
        logger.info("Safe Result: {}", safeResult);
    }
}