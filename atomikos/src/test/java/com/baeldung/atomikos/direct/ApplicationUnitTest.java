package com.baeldung.atomikos.direct;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;

public class ApplicationUnitTest {

    private static DataSource inventoryDataSource;
    private static DataSource orderDataSource;

    private static String productId = UUID.randomUUID()
        .toString();

    @Test
    @Ignore
    public void testPlaceOrderSuccess() throws Exception {
        int amount = 1;
        long initialBalance = getBalance(inventoryDataSource, productId);
        Application application = new Application(inventoryDataSource, orderDataSource);
        application.placeOrder(productId, amount);
        long finalBalance = getBalance(inventoryDataSource, productId);
        assertEquals(initialBalance - amount, finalBalance);
    }

    @Test
    @Ignore
    public void testPlaceOrderFailure() throws Exception {
        int amount = 10;
        long initialBalance = getBalance(inventoryDataSource, productId);
        Application application = new Application(inventoryDataSource, orderDataSource);
        application.placeOrder(productId, amount);
        long finalBalance = getBalance(inventoryDataSource, productId);
        assertEquals(initialBalance, finalBalance);
    }

    @BeforeClass
    public static void setUp() throws SQLException {

        inventoryDataSource = getDataSource("db1");
        orderDataSource = getDataSource("db2");
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

    private static DataSource getDataSource(String db) {

        DataSource ds;
        AtomikosDataSourceBean ads = new AtomikosDataSourceBean();
        ads.setXaDataSourceClassName("org.apache.derby.jdbc.EmbeddedXADataSource");
        Properties properties = new Properties();
        properties.put("databaseName", db);
        properties.put("createDatabase", "create");
        ads.setXaProperties(properties);
        ads.setUniqueResourceName(db);
        ads.setPoolSize(10); // optional
        ads.setBorrowConnectionTimeout(10); // optional
        ds = ads;
        return ds;

    }

    private static long getBalance(DataSource inventoryDataSource, String productId) throws Exception {

        UserTransactionImp utx = new UserTransactionImp();
        utx.begin();
        Connection inventoryConnection = inventoryDataSource.getConnection();
        Statement s1 = inventoryConnection.createStatement();
        String q1 = "select balance from Inventory where productId='" + productId + "'";
        ResultSet rs1 = s1.executeQuery(q1);
        if (rs1 == null || !rs1.next())
            throw new Exception("Product not found: " + productId);
        long balance = rs1.getLong(1);
        inventoryConnection.close();
        utx.commit();
        return balance;

    }

}
