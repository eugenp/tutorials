package com.baeldung.hexagonal.server.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import com.baeldung.hexagonal.application.cli.CliLibraryController;

@SpringBootApplication
@PropertySource(value = { "classpath:ddd-layers.properties" })
public class CommandLineApplication implements CommandLineRunner {

    public static void main(final String[] args) {
        SpringApplication application = new SpringApplication(CommandLineApplication.class);
        // uncomment to run just the console application
        // application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Autowired
    public CliLibraryController libraryController;

    @Autowired
    public ConfigurableApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
    	libraryController.bookIssue();
    	libraryController.bookReturn();
        // uncomment to stop the context when execution is done
        // context.close();
    }
}