package com.baeldung.properties.multipleyaml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultipleYamlApplication implements CommandLineRunner {

    @Autowired
    private MultipleYamlConfiguration config;

    public static void main(String[] args) {
        SpringApplication springApp = new SpringApplication(MultipleYamlApplication.class);

        // Code from first example, uncomment to use multiple profiles
        // springApp.setAdditionalProfiles("students", "teachers");

        springApp.run(args);
    }

    public void run(String... args) throws Exception {
        System.out.println("Students: " + config.getStudents());
        System.out.println("Teachers: " + config.getTeachers());
    }
}
