package com.baeldung.atomikos.spring.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.atomikos.spring.jpa.config.Config;
import com.baeldung.atomikos.spring.jpa.inventory.Inventory;
import com.baeldung.atomikos.spring.jpa.inventory.InventoryConfig;
import com.baeldung.atomikos.spring.jpa.inventory.InventoryRepository;
import com.baeldung.atomikos.spring.jpa.order.OrderConfig;
import com.baeldung.atomikos.spring.jpa.order.OrderRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Config.class, InventoryConfig.class, OrderConfig.class })
public class ApplicationUnitTest {

    private static String productId = UUID.randomUUID()
        .toString();

    @Autowired
    Application application;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @Ignore
    public void testPlaceOrderSuccess() throws Exception {
        int amount = 1;
        long initialBalance = getBalance(inventoryRepository, productId);
        application.placeOrder(productId, amount);
        long finalBalance = getBalance(inventoryRepository, productId);
        assertEquals(initialBalance - amount, finalBalance);
    }

    @Test
    @Ignore
    public void testPlaceOrderFailure() throws Exception {
        int amount = 10;
        long initialBalance = getBalance(inventoryRepository, productId);
        try {
            application.placeOrder(productId, amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        long finalBalance = getBalance(inventoryRepository, productId);
        assertEquals(initialBalance, finalBalance);
    }

    @Before
    public void setUp() throws SQLException {

        Inventory inventory = new Inventory();
        inventory.setProductId(productId);
        inventory.setBalance(new Long(10000));
        inventoryRepository.save(inventory);

    }

    private static long getBalance(InventoryRepository inventoryRepository, String productId) throws Exception {

        return inventoryRepository.findOne(productId)
            .getBalance();

    }

}
