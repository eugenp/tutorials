package com.baeldung.dddhexagonalspring;

import java.math.BigDecimal;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import com.baeldung.dddhexagonalspring.application.cli.CliOrderController;
import com.baeldung.dddhexagonalspring.domain.Product;

@SpringBootApplication
@PropertySource(value = { "classpath:ddd-layers.properties" })
public class DomainLayerApplication implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(DomainLayerApplication.class);

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
        LOG.info("Placing a new CLI order with two products");
        Product mobilePhone = new Product(UUID.randomUUID(), BigDecimal.valueOf(200), "mobile");
        Product razor = new Product(UUID.randomUUID(), BigDecimal.valueOf(50), "razor");
        LOG.info("Creating order with mobile phone");
        UUID orderId = orderController.createOrder(mobilePhone);
        LOG.info("Adding a razor to the order");
        orderController.addProduct(orderId, razor);
        LOG.info("Completing order");
        orderController.completeOrder(orderId);
        LOG.info("Order placement complete");
    }
}
