package com.baeldung.properties.yaml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YamlApplication implements CommandLineRunner {

    @Autowired
    private YamlFooProperties yamlFooProperties;

    public static void main(String[] args) {
        SpringApplication.run(YamlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("YAML Properties " + yamlFooProperties);
    }
}
