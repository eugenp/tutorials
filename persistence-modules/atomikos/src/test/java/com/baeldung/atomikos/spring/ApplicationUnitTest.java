package com.baeldung.atomikos.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.atomikos.spring.config.Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Config.class })
public class ApplicationUnitTest {

    private static String productId = UUID.randomUUID()
        .toString();

    @Autowired
    Application application;

    @Autowired
    DataSource inventoryDataSource;

    @Autowired
    DataSource orderDataSource;

    @Test
    @Ignore
    public void testPlaceOrderSuccess() throws Exception {
        int amount = 1;
        long initialBalance = getBalance(inventoryDataSource, productId);
        application.placeOrder(productId, amount);
        long finalBalance = getBalance(inventoryDataSource, productId);
        assertEquals(initialBalance - amount, finalBalance);
    }

    @Test
    @Ignore
    public void testPlaceOrderFailure() throws Exception {
        int amount = 10;
        long initialBalance = getBalance(inventoryDataSource, productId);
        try {
            application.placeOrder(productId, amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        long finalBalance = getBalance(inventoryDataSource, productId);
        assertEquals(initialBalance, finalBalance);
    }

    @Before
    public void setUp() throws SQLException {

        Connection inventoryConnection = inventoryDataSource.getConnection();
        Connection orderConnection = orderDataSource.getConnection();
        String createInventoryTable = "create table Inventory ( " + " productId VARCHAR ( 100 ) PRIMARY KEY, balance INT )";
        String createInventoryRow = "insert into Inventory values ( '" + productId + "', 10000 )";
        Statement s1 = inventoryConnection.createStatement();
        try {
            s1.executeUpdate(createInventoryTable);
        } catch (Exception e) {
            System.out.println("Inventory table exists");
        }
        try {
            s1.executeUpdate(createInventoryRow);
        } catch (Exception e) {
            System.out.println("Product row exists");
        }
        s1.close();
        String createOrderTable = "create table Orders ( orderId VARCHAR ( 100 ) PRIMARY KEY, productId VARCHAR ( 100 ), amount INT NOT NULL CHECK (amount <= 5) )";
        Statement s2 = orderConnection.createStatement();
        try {
            s2.executeUpdate(createOrderTable);
        } catch (Exception e) {
            System.out.println("Orders table exists");
        }
        s2.close();
        inventoryConnection.close();
        orderConnection.close();
    }

    private static long getBalance(DataSource inventoryDataSource, String productId) throws Exception {

        Connection inventoryConnection = inventoryDataSource.getConnection();
        Statement s1 = inventoryConnection.createStatement();
        String q1 = "select balance from Inventory where productId='" + productId + "'";
        ResultSet rs1 = s1.executeQuery(q1);
        if (rs1 == null || !rs1.next())
            throw new Exception("Product not found: " + productId);
        long balance = rs1.getLong(1);
        inventoryConnection.close();
        return balance;

    }

}
