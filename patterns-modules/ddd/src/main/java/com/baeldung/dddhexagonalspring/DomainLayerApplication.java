package com.baeldung.dddhexagonalspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import com.baeldung.dddhexagonalspring.application.cli.CliOrderController;

@SpringBootApplication
@PropertySource(value = { "classpath:ddd-layers.properties" })
public class DomainLayerApplication implements CommandLineRunner {

    public static void main(final String[] args) {
        SpringApplication application = new SpringApplication(DomainLayerApplication.class);
        // uncomment to run just the console application
        // application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Autowired
    public CliOrderController orderController;

    @Autowired
    public ConfigurableApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        orderController.createCompleteOrder();
        orderController.createIncompleteOrder();
        // uncomment to stop the context when execution is done
        // context.close();
    }
}
