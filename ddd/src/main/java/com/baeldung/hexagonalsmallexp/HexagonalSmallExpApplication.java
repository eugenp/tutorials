package com.baeldung.hexagonalsmallexp;

import com.baeldung.dddhexagonalspring.DomainLayerApplication;
import com.baeldung.hexagonalsmallexp.application.ProductController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.util.UUID;

@SpringBootApplication
@PropertySource(value = { "classpath:small-exp.properties" })
public class HexagonalSmallExpApplication implements CommandLineRunner {

    @Autowired
    private ProductController productController;

    @Autowired
    public ConfigurableApplicationContext context;

    public static void main(final String[] args) {
        SpringApplication application = new SpringApplication(HexagonalSmallExpApplication.class);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        UUID prodcutId = productController.createProduct();
        productController.findProduct(prodcutId);
    }
}
