package com.baeldung.jdbc.mocking;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.baeldung.jdbc.mocking.Customer.Status;

public class CustomersService {

    private final DataSource dataSource;

    CustomersService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Customer> customersEligibleForOffers() {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM customers");
            List<Customer> customers = new ArrayList<>();

            while (resultSet.next()) {
                Customer customer = mapCustomer(resultSet);
                if (customer.status() == Status.ACTIVE || customer.status() == Status.LOYAL) {
                    customers.add(customer);
                }
            }
            return customers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Customer mapCustomer(ResultSet resultSet) throws SQLException {
        return new Customer(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            Status.valueOf(resultSet.getString("status"))
        );
    }

}